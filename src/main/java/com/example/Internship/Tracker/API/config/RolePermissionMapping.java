package com.example.Internship.Tracker.API.config;

import com.example.Internship.Tracker.API.config.type.PermissionType;
import com.example.Internship.Tracker.API.config.type.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.Internship.Tracker.API.config.type.PermissionType.*;

public class RolePermissionMapping {

    private static final Map<RoleType, Set<PermissionType>> map = Map.of(
            RoleType.USER,Set.of(
                    USER_WRITE,
                    USER_READ,
                    USER_DELETE,
                    APPLICATION_READ,
                    APPLICATION_DELETE,
                    APPLICATION_WRITE,
                    COMPANY_READ,
                    INTERNSHIP_READ
            ),

            RoleType.RECRUITER,Set.of(
                    COMPANY_READ,
                    COMPANY_WRITE,
                    COMPANY_DELETE,
                    INTERNSHIP_READ,
                    INTERNSHIP_WRITE,
                    INTERNSHIP_DELETE,
                    USER_READ,
                    APPLICATION_READ
            ),

            RoleType.ADMIN,Set.of(
                    USER_WRITE,
                    USER_READ,
                    USER_DELETE,
                    APPLICATION_READ,
                    APPLICATION_DELETE,
                    APPLICATION_WRITE,
                    COMPANY_READ,
                    COMPANY_WRITE,
                    COMPANY_DELETE,
                    INTERNSHIP_READ,
                    INTERNSHIP_WRITE,
                    INTERNSHIP_DELETE

            )
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType role){
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
