package com.stefanovich.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanovich.dto.AuthLoginDto;
import com.stefanovich.model.Posts;
import com.stefanovich.model.Users;
import com.stefanovich.repository.PostsRepository;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class JsonLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final PostsRepository postsRepository;


    public JsonLoginFilter(AuthenticationManager authenticationManager, PostsRepository postsRepository) {
        this.setAuthenticationManager(authenticationManager);
        this.setFilterProcessesUrl("/api/auth/login");
        this.postsRepository = postsRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            CredentialsDto credentialsDto = new ObjectMapper().readValue(request.getInputStream(), CredentialsDto.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentialsDto.getEmail(), credentialsDto.getPassword());
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {

            throw new BadCredentialsException("bad credentials");
        }

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);

        Object principal = authResult.getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            Users user = ((UserDetailsImpl) principal).getUser();
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

            AuthLoginDto authLoginDto = new AuthLoginDto();
            authLoginDto.setResult(true);
            authLoginDto.setUser(authUserDto);

            response.getWriter().write(new ObjectMapper().writeValueAsString(authLoginDto));
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);


    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\n" +
                "\t\"result\": false\n" +
                "}");
    }

    @Data
    private static class CredentialsDto {
        @JsonProperty("e_mail")
        private String email;
        private String password;
    }

}
