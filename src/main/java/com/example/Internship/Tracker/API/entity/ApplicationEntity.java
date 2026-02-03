package com.example.Internship.Tracker.API.entity;

import com.example.Internship.Tracker.API.config.type.ApplicationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "application_table")
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "internship_id", nullable = false)
    private InternshipEntity internship;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @Column(nullable = false)
    private LocalDate appliedDate;

    private LocalDateTime lastUpdated;

    private String notes;
}
