package com.example.Internship.Tracker.API.dto.application_dto;

import com.example.Internship.Tracker.API.config.type.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationStatusCountDtoResponse {
    private  ApplicationStatus applicationStatus;
    private Long count;
}
