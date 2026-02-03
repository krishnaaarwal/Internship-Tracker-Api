package com.example.Internship.Tracker.API.dto.user_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3,max  =30,message = "Name size should be 3 to 30 characters")
    private String name;

    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
