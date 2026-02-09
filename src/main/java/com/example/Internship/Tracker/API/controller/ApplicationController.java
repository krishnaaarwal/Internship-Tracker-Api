package com.example.Internship.Tracker.API.controller;

import com.example.Internship.Tracker.API.dto.application_dto.ApplicationDtoRequest;
import com.example.Internship.Tracker.API.dto.application_dto.ApplicationDtoResponse;
import com.example.Internship.Tracker.API.dto.application_dto.ApplicationStatusCountDtoResponse;
import com.example.Internship.Tracker.API.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/applications/user/{userId}/all")
    public ResponseEntity<List<ApplicationDtoResponse>> getApplications(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.getApplications(userId));
    }

    @GetMapping("/applications/user/{userId}")
    public ResponseEntity<Page<ApplicationDtoResponse>> getApplicationsBasedOnAppliedDate(@PathVariable @Valid Long userId, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.applicationsOrderByAppliedDate(userId,pageable));
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id){
        applicationService.deleteApplication(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/applications")
    public ResponseEntity<ApplicationDtoResponse> createApplication(@RequestBody ApplicationDtoRequest application){
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.createApplication(application));
    }

    @PutMapping("/applications/{id}/status")
    public ResponseEntity<ApplicationDtoResponse> updateApplication(@PathVariable Long id,@RequestBody @Valid ApplicationDtoRequest application){
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.updateApplication(id,application));
    }

    @GetMapping("/applications/analytics/user/{userId}/status-count")
    public ResponseEntity<List<ApplicationStatusCountDtoResponse>> getUserApplicationStatus(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.countAllApplicationStatus(userId));
    }
}
