package com.example.Internship.Tracker.API.repository;

import com.example.Internship.Tracker.API.entity.InternshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InternshipRepository extends JpaRepository<InternshipEntity,Long> {
    List<InternshipEntity> findByCompanyId(Long companyId);
}
