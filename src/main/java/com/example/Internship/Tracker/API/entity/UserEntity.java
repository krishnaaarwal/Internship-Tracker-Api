package com.example.Internship.Tracker.API.entity;

import com.example.Internship.Tracker.API.config.RolePermissionMapping;
import com.example.Internship.Tracker.API.config.type.AuthProviderType;
import com.example.Internship.Tracker.API.config.type.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class UserEntity implements UserDetails {
    //ORM (Object relation mapping)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String email;

    @Column()
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<ApplicationEntity> applications;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream().
//        map(roleType -> new SimpleGrantedAuthority("ROLE_"+roleType.name()))
//                .collect(Collectors.toSet());                                                     //BEFORE PERMISSIONS


        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for(RoleType role : roles) {

            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

            authorities.addAll(RolePermissionMapping.getAuthoritiesForRole(role));
        }

        return authorities;

    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}
