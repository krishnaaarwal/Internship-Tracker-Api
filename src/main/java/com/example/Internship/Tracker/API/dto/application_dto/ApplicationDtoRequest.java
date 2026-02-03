package com.example.Internship.Tracker.API.dto.application_dto;
import com.example.Internship.Tracker.API.config.type.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDtoRequest {

    private Long userId;

    private Long internshipId;

    private ApplicationStatus applicationStatus;

    private LocalDate appliedDate;

    private LocalDateTime lastUpdated;

    private String notes;
}
