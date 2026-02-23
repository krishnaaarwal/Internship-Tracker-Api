package com.example.Internship.Tracker.API.dto.user_dto;

import com.example.Internship.Tracker.API.config.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse implements Serializable {
    private Long id;
    private String email;
    private Set<RoleType> roles;
}
