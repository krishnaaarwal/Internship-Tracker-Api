package com.example.Internship.Tracker.API.service;

import com.example.Internship.Tracker.API.dto.internship_dto.InternshipDtoRequest;
import com.example.Internship.Tracker.API.dto.internship_dto.InternshipDtoResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface InternshipService {
    InternshipDtoResponse createInternship(@Valid InternshipDtoRequest internshipDtoRequest);

    List<InternshipDtoResponse> getAllInternships();

    InternshipDtoResponse getInternshpById(Long id);

    List<InternshipDtoResponse> getInternshipByCompanyId(@Valid Long companyId);

    void deleteInternship(Long id);
}
