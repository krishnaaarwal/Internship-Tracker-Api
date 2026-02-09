package com.example.Internship.Tracker.API.controller;

import com.example.Internship.Tracker.API.dto.auth_dto.LoginRequestDto;
import com.example.Internship.Tracker.API.dto.auth_dto.LoginResponseDto;
import com.example.Internship.Tracker.API.dto.auth_dto.SignupResponseDto;
import com.example.Internship.Tracker.API.security.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto body){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(body));
    }
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupResponseDto body){
        return ResponseEntity.status(HttpStatus.OK).body(authService.signup(body));
    }
}
