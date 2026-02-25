package com.example.Internship.Tracker.API.repository;

import com.example.Internship.Tracker.API.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {
}
