package com.example.Internship.Tracker.API.dto.company_dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDtoRequest {
    @NotBlank(message = "Company name cannot be blank")
    private String name;

    @URL
    @NotBlank(message = "Company website cannot be blank")
    private String website;

    @NotBlank(message = "Company location cannot be blank")
    private String location;
}
