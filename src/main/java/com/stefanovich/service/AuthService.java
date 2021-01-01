package com.stefanovich.service;


import com.github.cage.Cage;
import com.github.cage.GCage;
import com.stefanovich.dto.AddUserDto;
import com.stefanovich.dto.AuthLoginDto;
import com.stefanovich.dto.CaptchaDto;
import com.stefanovich.dto.ChangePasswordDto;
import com.stefanovich.model.CaptchaCodes;
import com.stefanovich.model.Posts;
import com.stefanovich.model.Users;
import com.stefanovich.repository.CaptchaCodesRepository;
import com.stefanovich.repository.PostsRepository;
import com.stefanovich.repository.UsersRepository;
import com.stefanovich.security.EmailService;
import com.stefanovich.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PostsRepository postsRepository;
    private final CaptchaCodesRepository captchaCodesRepository;

    public Users getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = null;
        if (principal instanceof UserDetailsImpl)
            user = ((UserDetailsImpl) principal).getUser();
        return user;
    }


    public AuthLoginDto getCheck() {
        AuthLoginDto authLoginDto = new AuthLoginDto();
        authLoginDto.setResult(false);

        if (getCurrentUser() == null) {
            return authLoginDto;
        }

        Users user = getCurrentUser();
        List<Posts> posts = postsRepository.findAllUserId(user.getId());

        AuthLoginDto.AuthUserDto authUserDto = new AuthLoginDto.AuthUserDto();
        authUserDto.setEmail(user.getEmail());
        authUserDto.setId(user.getId());
        authUserDto.setName(user.getName());
        authUserDto.setPhoto(user.getPhoto());
        authUserDto.setModeration(user.isModerator());
        if (!user.isModerator()) {
            authUserDto.setModerationCount(0);
        } else {
            authUserDto.setModerationCount(posts.size());
        }
        authUserDto.setSettings(true);

        authLoginDto.setResult(true);
        authLoginDto.setUser(authUserDto);

        return authLoginDto;
    }


    public Map<String, Object> addUser(AddUserDto addUserDto) {

        Optional<Users> byEmail = usersRepository.findByEmail(addUserDto.getEmail());
        if (byEmail.isPresent()) {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "email", "Этот e-mail уже зарегистрирован"
                    ));

        }

        Optional<CaptchaCodes> byCode = captchaCodesRepository.findByCapcha(addUserDto.getCaptcha());

        if (byCode.isPresent()) {
            CaptchaCodes captchaCodes = byCode.get();
            if (LocalDateTime.now().minusDays(1).isAfter(captchaCodes.getTime())) {
                return Map.of(
                        "result", false,
                        "errors", Map.of(
                                "captcha", "Код с картинки введён неверно"
                        )
                );
            }
        }


        Users user = new Users();
        user.setName(addUserDto.getName());
        user.setEmail(addUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(addUserDto.getPassword()));
        user.setRegTime(LocalDateTime.now());
        user.setModerator(false);
        usersRepository.save(user);

        return Map.of("result", true);

    }


    public Boolean restoreEmail(String email) {
        List<Users> users = usersRepository.findByQuery(email);

        if (users.size() != 1) {
            return false;
        }
        Users user = users.get(0);
        String code = generateToken();
        user.setCode(code);
        usersRepository.save(user);


        emailService.send(user.getEmail(), "ввостановление пароля",
                "http://localhost:8080/login/change-password/" + code);

        return true;

    }

    private String generateToken() {
        return UUID.randomUUID().toString() +
                UUID.randomUUID().toString();
    }


    public Map<String, Object> change(ChangePasswordDto changePasswordDto) {
        Optional<CaptchaCodes> byCode = captchaCodesRepository.findByCode(changePasswordDto.getCaptcha(), changePasswordDto.getCaptchaSecret());

        if (byCode.isPresent()) {
            CaptchaCodes captchaCodes = byCode.get();
            if (LocalDateTime.now().minusDays(1).isAfter(captchaCodes.getTime())) {
                return Map.of(
                        "result", false,
                        "errors", Map.of(
                                "code", "Ссылка для восстановления пароля устарела.\n" +
                                        "\t\t\t\t<a href=\n" +
                                        "\t\t\t\t\\\"/auth/restore\\\">Запросить ссылку снова</a>"
                        )
                );
            } else {
                return Map.of("result", true);
            }
        } else {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "captcha", "Код с картинки введён неверно"
                    )
            );
        }
    }

    public CaptchaDto makeCaptchaDto() {
        Cage cage = new GCage();

        String secretCode = UUID.randomUUID().toString();
        String code = String.valueOf(new Random().nextInt(10000));
        byte[] fileContent = cage.draw(code);

        String encodedString = Base64.getEncoder().encodeToString(fileContent);


        CaptchaCodes captchaCodes = new CaptchaCodes();
        captchaCodes.setSecretCode(secretCode);
        captchaCodes.setCode(code);
        captchaCodes.setTime(LocalDateTime.now());
        captchaCodesRepository.save(captchaCodes);
        return CaptchaDto.builder()
                .image("data:image/png;base64, " + encodedString)
                .secret(secretCode).build();
    }


}
