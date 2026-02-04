package com.example.Internship.Tracker.API.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "internship_table")
public class InternshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String role;

    @Min(0)
    private Long stipend;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)    //Owning side
    private CompanyEntity company;

    @OneToMany(mappedBy = "internship",cascade = CascadeType.REMOVE)
    private List<ApplicationEntity> applications;
}

