package com.example.Internship.Tracker.API.security;

import com.example.Internship.Tracker.API.dto.auth_dto.LoginRequestDto;
import com.example.Internship.Tracker.API.dto.auth_dto.LoginResponseDto;
import com.example.Internship.Tracker.API.dto.auth_dto.SignupResponseDto;
import com.example.Internship.Tracker.API.entity.UserEntity;
import com.example.Internship.Tracker.API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(LoginRequestDto body) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.getUsername(),body.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

//    public SignupResponseDto signup(SignupResponseDto body) {
//        UserEntity user = userRepository.findByUsername(body.getUsername()).orElseThrow(()->new IllegalArgumentException("User already exist"));
//
////        user = userRepository.save(new U)
//    }
}
