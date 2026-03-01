package com.example.Internship.Tracker.API.controller;

import com.example.Internship.Tracker.API.dto.company_dto.CompanyDtoRequest;
import com.example.Internship.Tracker.API.dto.company_dto.CompanyDtoResponse;
import com.example.Internship.Tracker.API.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/public/company")
    public ResponseEntity<List<CompanyDtoResponse>> getAllCompany(){
        return  ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompany());

    }

    @GetMapping("/public/company/{id}")
    public ResponseEntity<CompanyDtoResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }

    @PostMapping("/company")
    public ResponseEntity<CompanyDtoResponse> createCompany(@RequestBody CompanyDtoRequest companyDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(companyDtoRequest));
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<CompanyDtoResponse> updateCompany(@PathVariable Long id , @RequestBody @Valid CompanyDtoRequest updatedCompany){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateCompany(id,updatedCompany));
    }

    @PatchMapping("/company/{id}")
    public  ResponseEntity<CompanyDtoResponse> updatePartialCompany(@PathVariable Long id, Map<String,Object> updates){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updatePartialCompany(id,updates));
    }


    @DeleteMapping("/company/{id}")
    public void deleteCompany(@PathVariable Long id){
        companyService.deleteCompany(id);
        ResponseEntity.noContent().build();
    }


}
