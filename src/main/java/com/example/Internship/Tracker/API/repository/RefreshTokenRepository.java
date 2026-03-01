package com.example.Internship.Tracker.API.repository;

import com.example.Internship.Tracker.API.entity.RefreshTokenEntity;
import com.example.Internship.Tracker.API.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Query("delete from RefreshTokenEntity r where r.user.id = :userId")
    void deleteByUserId(Long userId);
}
