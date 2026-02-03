package com.example.Internship.Tracker.API.dto.internship_dto;

import com.example.Internship.Tracker.API.dto.company_dto.CompanyDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipDtoResponse {
    private Long id;
    private String role;
    private Long stipend;
    private CompanyDtoResponse company;
}
