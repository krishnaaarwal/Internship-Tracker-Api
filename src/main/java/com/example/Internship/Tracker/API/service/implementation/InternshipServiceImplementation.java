package com.example.Internship.Tracker.API.service.implementation;

import com.example.Internship.Tracker.API.dto.internship_dto.InternshipDtoRequest;
import com.example.Internship.Tracker.API.dto.internship_dto.InternshipDtoResponse;
import com.example.Internship.Tracker.API.entity.CompanyEntity;
import com.example.Internship.Tracker.API.entity.InternshipEntity;
import com.example.Internship.Tracker.API.repository.CompanyRepository;
import com.example.Internship.Tracker.API.repository.InternshipRepository;
import com.example.Internship.Tracker.API.service.CompanyService;
import com.example.Internship.Tracker.API.service.InternshipService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipServiceImplementation implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private static final String CACHE = "internship";

    @PreAuthorize("hasAuthority('INTERNSHIP_WRITE')")
    @CachePut(cacheNames = CACHE, key = "#result.id")
    @Override
    public InternshipDtoResponse createInternship(InternshipDtoRequest internshipDtoRequest) {

        Long companyId = internshipDtoRequest.getCompanyId();

        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Company not found with id: " + companyId));

        InternshipEntity internship = modelMapper.map(internshipDtoRequest, InternshipEntity.class);
        internship.setCompany(company);

        InternshipEntity saved = internshipRepository.save(internship);

        return modelMapper.map(saved, InternshipDtoResponse.class);
    }

    @PreAuthorize("hasAuthority('INTERNSHIP_READ')")
    @Override
    public List<InternshipDtoResponse> getAllInternships() {
        return internshipRepository.findAll().stream()
                .map(internshipEntity -> modelMapper.map(internshipEntity,InternshipDtoResponse.class))
                .toList();
    }

    @PreAuthorize("hasAuthority('INTERNSHIP_READ')")
    @Cacheable(cacheNames = CACHE,key = "#id")
    @Override
    public InternshipDtoResponse getInternshpById(Long id) {
        InternshipEntity internshipEntity= internshipRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Internship with id:"+id+" not found"));
        return modelMapper.map(internshipEntity,InternshipDtoResponse.class);
    }

    @PreAuthorize("hasAuthority('INTERNSHIP_READ')")
    @Override
    public List<InternshipDtoResponse> getInternshipByCompanyId(Long companyId) {
        companyRepository.findById(companyId);

        List<InternshipEntity> listOfEntity = internshipRepository.findByCompanyId(companyId);

        return listOfEntity.stream().map(internshipEntity -> modelMapper.map(internshipEntity,InternshipDtoResponse.class)).toList();
    }

    @PreAuthorize("hasAuthority('INTERNSHIP_DELETE') and (@authz.isInternshipOwner(#id) or @authz.isAdmin())")
    @CacheEvict(cacheNames = CACHE,key="#id")
    @Override
    public void deleteInternship(Long id) {

        InternshipEntity internship = internshipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Internship not exist:"+id));

        internshipRepository.delete(internship);
    }


}
