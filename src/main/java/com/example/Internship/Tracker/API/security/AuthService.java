package com.example.Internship.Tracker.API.security;

import com.example.Internship.Tracker.API.dto.auth_dto.LoginRequestDto;
import com.example.Internship.Tracker.API.dto.auth_dto.LoginResponseDto;
import com.example.Internship.Tracker.API.dto.auth_dto.SignupRequestDto;
import com.example.Internship.Tracker.API.dto.auth_dto.SignupResponseDto;
import com.example.Internship.Tracker.API.entity.UserEntity;
import com.example.Internship.Tracker.API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(LoginRequestDto body) {
        // 1. AuthenticationManager delegates to AuthenticationProvider
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword())
        );
        // Actually, AuthenticationManager → ProviderManager → DaoAuthenticationProvider
        // DaoAuthenticationProvider uses UserDetailsService + PasswordEncoder

        // 2. Principal is the authenticated user (UserDetails implementation)
        UserEntity user = (UserEntity) authentication.getPrincipal();

        // 3. Generate token
        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public SignupResponseDto signup(SignupRequestDto body) {
       UserEntity user = userRepository.findByUsername(body.getUsername()).orElse(null);

       if(user!=null)
           throw new IllegalArgumentException("User already exists");

        user = userRepository.save(UserEntity.builder().username(body.getUsername()).password(passwordEncoder.encode(body.getPassword())).email(body.getEmail()).build());

        return modelMapper.map(user,SignupResponseDto.class);
    }
}
