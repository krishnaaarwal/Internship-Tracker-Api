package com.example.Internship.Tracker.API.repository;

import com.example.Internship.Tracker.API.config.type.AuthProviderType;
import com.example.Internship.Tracker.API.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);

}
