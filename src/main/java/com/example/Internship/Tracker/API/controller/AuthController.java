package com.example.Internship.Tracker.API.controller;

import com.example.Internship.Tracker.API.dto.auth_dto.*;
import com.example.Internship.Tracker.API.entity.RefreshTokenEntity;
import com.example.Internship.Tracker.API.entity.UserEntity;
import com.example.Internship.Tracker.API.security.service.AuthService;
import com.example.Internship.Tracker.API.security.service.RefreshTokenService;
import com.example.Internship.Tracker.API.security.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthUtil authUtil;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto body){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(body));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto body){
        return ResponseEntity.status(HttpStatus.OK).body(authService.signup(body));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto body){
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(body));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto body){
        authService.logout(body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
