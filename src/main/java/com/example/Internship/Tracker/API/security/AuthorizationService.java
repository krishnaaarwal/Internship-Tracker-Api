package com.example.Internship.Tracker.API.security;

import com.example.Internship.Tracker.API.entity.ApplicationEntity;
import com.example.Internship.Tracker.API.entity.UserEntity;
import com.example.Internship.Tracker.API.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("authz")
@RequiredArgsConstructor
public class AuthorizationService {
    private final ApplicationRepository applicationRepository;

    public boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isOwner(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return user.getId().equals(userId);
    }

    public boolean isApplicationOwner(Long applicationId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();

        ApplicationEntity application = applicationRepository.findById(applicationId).orElseThrow(()->new IllegalArgumentException("Application not found"));

        return application.getUser().getId().equals(user.getId());
    }

    public boolean belongsToCompany(Long companyId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();

        if (user.getCompany() == null) return false;

        return user.getCompany().getId().equals(companyId);
    }
}
