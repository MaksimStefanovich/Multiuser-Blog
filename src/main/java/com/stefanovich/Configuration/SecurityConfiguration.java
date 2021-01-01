package com.stefanovich.Configuration;

import com.stefanovich.repository.PostsRepository;
import com.stefanovich.security.CustomLogoutSuccessHandler;
import com.stefanovich.security.JsonLoginFilter;
import com.stefanovich.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PostsRepository postsRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterBefore(jsonLoginFilter(postsRepository), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/init").permitAll()

                .antMatchers(HttpMethod.GET,"/api/post/moderation").hasAnyAuthority(UserRole.USER.name(), UserRole.MODERATOR.name())
                .antMatchers("/api/post/my").hasAuthority(UserRole.USER.name())
                .antMatchers("/api/post").hasAnyAuthority(UserRole.USER.name(), UserRole.MODERATOR.name())
                .antMatchers("/api/post/like").hasAuthority(UserRole.USER.name())
                .antMatchers("/api/post/dislike").hasAuthority(UserRole.USER.name())
                .antMatchers("/api/post/image").hasAnyAuthority(UserRole.USER.name(), UserRole.MODERATOR.name())

                .antMatchers(HttpMethod.GET, "/api/post/**").permitAll()


                .antMatchers(HttpMethod.PUT, "/api/post/**").hasAnyAuthority(UserRole.USER.name(), UserRole.MODERATOR.name())
                .antMatchers("/api/comment/**").hasAnyAuthority(UserRole.USER.name(), UserRole.MODERATOR.name())
                .antMatchers("/api/tag/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/moderation").hasAuthority(UserRole.USER.name())
                .antMatchers("/api/calendar/**").permitAll()
                .antMatchers("/api/auth/profile/my").hasAnyAuthority(UserRole.USER.name(), UserRole.MODERATOR.name())
                .antMatchers("/api/auth/logout").hasAnyAuthority(UserRole.USER.name(), UserRole.MODERATOR.name())
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/statistics/my").hasAnyAuthority(UserRole.USER.name(), UserRole.MODERATOR.name())
                .antMatchers("/api/statistics/all").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/settings/").hasAuthority(UserRole.MODERATOR.name())
                .antMatchers(HttpMethod.GET, "/api/settings/").permitAll()


                .anyRequest().permitAll()
                .and().formLogin()
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public JsonLoginFilter jsonLoginFilter(PostsRepository postsRepository) throws Exception {
        return new JsonLoginFilter(authenticationManager(), postsRepository);
    }


}
