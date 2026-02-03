package com.example.Internship.Tracker.API.service;

import com.example.Internship.Tracker.API.config.type.ApplicationStatus;
import com.example.Internship.Tracker.API.dto.application_dto.ApplicationDtoRequest;
import com.example.Internship.Tracker.API.dto.application_dto.ApplicationDtoResponse;
import com.example.Internship.Tracker.API.dto.application_dto.ApplicationStatusCountDtoResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationService {

    List<ApplicationDtoResponse> getApplications(Long userId);
    void deleteApplication(Long id);
    ApplicationDtoResponse createApplication(ApplicationDtoRequest application);
    ApplicationDtoResponse updateApplication(Long id,ApplicationDtoRequest application);

    @Transactional
    ApplicationDtoResponse updateStaus(Long id, ApplicationStatus newStatus);

    List<ApplicationStatusCountDtoResponse> countAllApplicationStatus(Long userId);

    @Transactional
    Page<ApplicationDtoResponse> applicationsOrderByAppliedDate(Long userId, Pageable pageable);
}
