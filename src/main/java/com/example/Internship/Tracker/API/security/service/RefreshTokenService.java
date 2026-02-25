package com.example.Internship.Tracker.API.security.service;

import com.example.Internship.Tracker.API.entity.RefreshTokenEntity;
import com.example.Internship.Tracker.API.repository.RefreshTokenRepository;
import com.example.Internship.Tracker.API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenEntity refreshToken(Long id){
        RefreshTokenEntity refreshToken = RefreshTokenEntity
                .builder()
                .user(userRepository.findById(id).get())
                .token(UUID.randomUUID().toString())
                .expirationTime(Instant.now().plusMillis(1000*60*15))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }
}
