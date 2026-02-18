package com.example.Internship.Tracker.API.controller;

import com.example.Internship.Tracker.API.dto.internship_dto.InternshipDtoRequest;
import com.example.Internship.Tracker.API.dto.internship_dto.InternshipDtoResponse;
import com.example.Internship.Tracker.API.service.InternshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InternshipController {
    private final InternshipService internshipService;


    @PostMapping("/internships")
    public ResponseEntity<InternshipDtoResponse> createInternship(@RequestBody @Valid InternshipDtoRequest internshipDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(internshipService.createInternship(internshipDtoRequest));
    }


    @GetMapping("/internships")
    public List<InternshipDtoResponse> getAllInternships(){
        return internshipService.getAllInternships();
    }


    @GetMapping("/internships/{id}")
    public ResponseEntity<InternshipDtoResponse> getInternshipById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(internshipService.getInternshpById(id));
    }


    @GetMapping("internships/company/{companyId}")
    public ResponseEntity<List<InternshipDtoResponse>> getInternshipByCompanyId(@PathVariable @Valid Long companyId){
        return ResponseEntity.status(HttpStatus.OK).body(internshipService.getInternshipByCompanyId(companyId));
    }
}
