package com.example.Internship.Tracker.API.repository;

import com.example.Internship.Tracker.API.dto.application_dto.ApplicationStatusCountDtoResponse;
import com.example.Internship.Tracker.API.entity.ApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// com.example.Internship.Tracker.API.dto.application_dto.ApplicationStatusCountDtoResponse

public interface ApplicationRepository extends JpaRepository<ApplicationEntity,Long> {
    List<ApplicationEntity> findByUserId(Long userId);

    @Query("SELECT new com.example.Internship.Tracker.API.dto.application_dto.ApplicationStatusCountDtoResponse(a.ApplicationStatus , COUNT(a))"
     + "FROM ApplicationEntity a" +
    "GROUP by a.ApplicationStatus")
    List<ApplicationStatusCountDtoResponse> groupApplications(Long userId);

    Page<ApplicationEntity> findByUserIdOrderByAppliedDateDesc(Long userId, Pageable pageable);

}
