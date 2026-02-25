package com.example.Internship.Tracker.API.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Builder
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
