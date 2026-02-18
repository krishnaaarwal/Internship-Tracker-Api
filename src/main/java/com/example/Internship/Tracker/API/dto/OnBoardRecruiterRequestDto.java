package com.example.Internship.Tracker.API.dto;

import com.example.Internship.Tracker.API.config.type.RoleType;
import com.example.Internship.Tracker.API.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnBoardRecruiterRequestDto {
    private Long userId;
    private Long companyId;
}
