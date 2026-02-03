package com.example.Internship.Tracker.API.dto.internship_dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipDtoRequest {
    @NotBlank
    private String role;

    @Min(0)
    private Long stipend;

    @NotBlank
    @Id
    private Long companyId;
}
