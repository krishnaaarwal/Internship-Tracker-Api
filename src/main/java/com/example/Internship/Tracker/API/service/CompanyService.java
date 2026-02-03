package com.example.Internship.Tracker.API.service;

import com.example.Internship.Tracker.API.dto.company_dto.CompanyDtoRequest;
import com.example.Internship.Tracker.API.dto.company_dto.CompanyDtoResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    List<CompanyDtoResponse> getAllCompany();
    CompanyDtoResponse getCompanyById(Long id);

   CompanyDtoResponse createCompany(CompanyDtoRequest companyDtoRequest);

     CompanyDtoResponse updateCompany(Long id,@Valid CompanyDtoRequest updatedCompany);

    CompanyDtoResponse updatePartialCompany(Long id, Map<String, Object> updates);

    void deleteCompany(Long id);
}
