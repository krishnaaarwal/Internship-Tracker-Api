package com.example.Internship.Tracker.API.dto.company_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDtoResponse implements Serializable {
    private Long id;
    private String name;
    private String website;
    private String location;
}
