package com.example.Internship.Tracker.API.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "company_table")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = true)
    private String website;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "company")      //Inverse side
    private List<InternshipEntity> internships;
}
