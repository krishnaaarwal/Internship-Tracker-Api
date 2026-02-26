package com.example.Internship.Tracker.API.security.service;

import com.example.Internship.Tracker.API.entity.RefreshTokenEntity;
import com.example.Internship.Tracker.API.entity.UserEntity;
import com.example.Internship.Tracker.API.error.RefreshTokenExpiredException;
import com.example.Internship.Tracker.API.repository.RefreshTokenRepository;
import com.example.Internship.Tracker.API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenEntity generateRefreshToken(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expirationTime(Instant.now().plus(7, ChronoUnit.DAYS))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshTokenEntity> findToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity verifyAndRotate(RefreshTokenEntity token) {
        if (token.getExpirationTime().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException("Refresh token expired: " + token.getToken());
        }

        RefreshTokenEntity refreshTokenEntity = generateRefreshToken(token.getUser().getId());
        refreshTokenRepository.delete(token);
        return refreshTokenEntity;

    }
}
