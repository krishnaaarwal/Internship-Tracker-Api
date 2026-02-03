package com.example.Internship.Tracker.API.repository;

import com.example.Internship.Tracker.API.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {
}
