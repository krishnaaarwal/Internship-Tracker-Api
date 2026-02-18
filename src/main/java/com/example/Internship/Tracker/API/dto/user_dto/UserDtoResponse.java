package com.example.Internship.Tracker.API.dto.user_dto;

import com.example.Internship.Tracker.API.config.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {
    private Long id;
    private String email;
    private Set<RoleType> roles;
}
