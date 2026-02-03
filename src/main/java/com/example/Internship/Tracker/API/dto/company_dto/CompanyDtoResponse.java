package com.example.Internship.Tracker.API.dto.company_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDtoResponse {
    private String id;
    private String name;
    private String website;
    private String location;
}
