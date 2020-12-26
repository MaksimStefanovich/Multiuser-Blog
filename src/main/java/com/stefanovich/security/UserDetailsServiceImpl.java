package com.stefanovich.security;

import com.stefanovich.dto.AuthLoginDto;
import com.stefanovich.model.Users;
import com.stefanovich.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(login).orElseThrow(() -> new UsernameNotFoundException("пользователь не найден"));


        return new UserDetailsImpl(user);


    }
}
