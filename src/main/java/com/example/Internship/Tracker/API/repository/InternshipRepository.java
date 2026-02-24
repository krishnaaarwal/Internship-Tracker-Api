package com.example.Internship.Tracker.API.repository;

import com.example.Internship.Tracker.API.entity.InternshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface InternshipRepository extends JpaRepository<InternshipEntity,Long> {
    List<InternshipEntity> findByCompanyId(Long companyId);
}
