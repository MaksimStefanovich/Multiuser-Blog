package com.stefanovich.Configuration;

import com.stefanovich.repository.PostsRepository;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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


                .antMatchers("/api/post/moderation").hasAuthority(UserRole.MODERATOR.name())
                .antMatchers("/api/post/my").hasAuthority(UserRole.USER.name())
                .antMatchers("/api/post").hasAuthority(UserRole.USER.name())
                .antMatchers("/api/post/like").hasAnyAuthority()

                .antMatchers(HttpMethod.GET, "/api/post/**").permitAll()

                //TODO лучше hasAnyRole или hasAnyAuthority
                .antMatchers("/api/image").hasAnyRole()

                .antMatchers(HttpMethod.PUT, "/api/post/**").hasAnyAuthority()
                .antMatchers("/api/comment/**").hasAnyRole()
                .antMatchers("/api/tag/**").permitAll()
                .antMatchers("/api/moderation").hasAuthority(UserRole.MODERATOR.name())
                .antMatchers("/api/calendar/**").permitAll()
                .antMatchers("/api/auth/profile/my").hasAnyRole()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/statistics/my").hasAnyAuthority(UserRole.USER.name())
                .antMatchers("/api/statistics/all").permitAll()


                .anyRequest().permitAll()
                .and().formLogin()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public JsonLoginFilter jsonLoginFilter(PostsRepository postsRepository) throws Exception {
        return new JsonLoginFilter(authenticationManager(), postsRepository);
    }


}
