package com.example.Internship.Tracker.API.dto.user_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {
    private Long id;
    private String name;
    private String email;
}
