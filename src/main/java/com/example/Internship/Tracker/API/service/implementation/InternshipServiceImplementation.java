package com.example.Internship.Tracker.API.service.implementation;

import com.example.Internship.Tracker.API.dto.internship_dto.InternshipDtoRequest;
import com.example.Internship.Tracker.API.dto.internship_dto.InternshipDtoResponse;
import com.example.Internship.Tracker.API.entity.InternshipEntity;
import com.example.Internship.Tracker.API.repository.InternshipRepository;
import com.example.Internship.Tracker.API.service.CompanyService;
import com.example.Internship.Tracker.API.service.InternshipService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipServiceImplementation implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Override
    public InternshipDtoResponse createInternship(InternshipDtoRequest internshipDtoRequest) {
        Long companyId = internshipDtoRequest.getCompanyId();
        InternshipEntity internshipEntity = internshipRepository.findById(companyId).orElseThrow(()->new EntityNotFoundException("Company doesn't exist with id:"+companyId));
        InternshipEntity saved = internshipRepository.save(internshipEntity);
        return modelMapper.map(saved, InternshipDtoResponse.class);
    }

    @Override
    public List<InternshipDtoResponse> getAllInternships() {
        return internshipRepository.findAll().stream()
                .map(internshipEntity -> modelMapper.map(internshipEntity,InternshipDtoResponse.class))
                .toList();
    }

    @Override
    public InternshipDtoResponse getInternshpById(Long id) {
        InternshipEntity internshipEntity= internshipRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Internship with id:"+id+" not found"));
        return modelMapper.map(internshipEntity,InternshipDtoResponse.class);
    }

    @Override
    public List<InternshipDtoResponse> getInternshipByCompanyId(Long companyId) {
        companyService.getCompanyById(companyId);

        List<InternshipEntity> listOfEntity = internshipRepository.findByCompanyId(companyId);

        return listOfEntity.stream().map(internshipEntity -> modelMapper.map(internshipEntity,InternshipDtoResponse.class)).toList();
    }


}
