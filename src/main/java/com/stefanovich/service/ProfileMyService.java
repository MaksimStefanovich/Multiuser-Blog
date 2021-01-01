package com.stefanovich.service;

import com.stefanovich.dto.ProfileMyDto;
import com.stefanovich.model.Users;
import com.stefanovich.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProfileMyService {
    private final AuthService authService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;

    @Value("${upload.file.extension}")
    private List<String> extension;

    @Value("${user.avatar.size}")
    private Integer userAvatarSize;


    public Map<String, Object> changePhotoAndPassword(String name, String email, String password,
                                                      MultipartFile photo) throws IOException {
        if (!extension.contains(FilenameUtils.getExtension(photo.getOriginalFilename()))) {
            throw new RuntimeException();
        }

        Users user = authService.getCurrentUser();

        Optional<Users> byEmail = usersRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "email", "Этот e-mail уже зарегистрирован"
                    ));
        }

        if (password == null || password.length() < 6) {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "password", "Пароль короче 6-ти символов"
                    ));

        }

        if (name == null || name.length() < 2) {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "name", "Имя указано неверно"
                    ));

        }

        ByteArrayInputStream bis = new ByteArrayInputStream(photo.getBytes());
        BufferedImage image = ImageIO.read(bis);

        BufferedImage newImage = Scalr.resize(image, Scalr.Method.SPEED, userAvatarSize, userAvatarSize);
        BufferedImage newImage1 = Scalr.resize(newImage, Scalr.Method.ULTRA_QUALITY, userAvatarSize, userAvatarSize);


        File file = postService.createFile();

        ImageIO.write(newImage1, "jpg", file);

        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoto(file.getPath());
        usersRepository.save(user);

        return Map.of("result", true);
    }


    public Map<String, Object> changeNameAndEmail(ProfileMyDto profileMyDto) {

        Users user = authService.getCurrentUser();

        if (profileMyDto.getName().length() < 2) {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "name", "Имя указано неверно"
                    ));

        }

        Optional<Users> byEmail = usersRepository.findByEmail(profileMyDto.getEmail());
        if (byEmail.isPresent()) {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "email", "Этот e-mail уже зарегистрирован"
                    ));
        }

        user.setName(profileMyDto.getName());
        user.setEmail(profileMyDto.getEmail());
        usersRepository.save(user);

        return Map.of("result", true);

    }


    public Map<String, Object> changeNameAndPasswordAndEmail(ProfileMyDto profileMyDto) {
        Users user = authService.getCurrentUser();

        if (profileMyDto.getName().length() < 2) {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "name", "Имя указано неверно"
                    ));

        }

        Optional<Users> byEmail = usersRepository.findByEmail(profileMyDto.getEmail());
        if (byEmail.isPresent()) {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "email", "Этот e-mail уже зарегистрирован"
                    ));
        }

        if (profileMyDto.getPassword().length() < 6) {
            return Map.of(
                    "result", false,
                    "errors", Map.of(
                            "password", "Пароль короче 6-ти символов"
                    ));

        }

        user.setName(profileMyDto.getName());
        user.setEmail(profileMyDto.getEmail());
        user.setPassword(passwordEncoder.encode(profileMyDto.getPassword()));
        usersRepository.save(user);

        return Map.of("result", true);
    }

    public Map<String, Object> removePhoto() {
        Users user = authService.getCurrentUser();
        user.setPhoto("");
        usersRepository.save(user);


        return Map.of("result", true);


    }
}
