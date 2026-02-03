package com.example.Internship.Tracker.API.service.implementation;

import com.example.Internship.Tracker.API.dto.company_dto.CompanyDtoRequest;
import com.example.Internship.Tracker.API.dto.company_dto.CompanyDtoResponse;
import com.example.Internship.Tracker.API.entity.CompanyEntity;
import com.example.Internship.Tracker.API.repository.CompanyRepository;
import com.example.Internship.Tracker.API.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CompanyDtoResponse> getAllCompany() {
      List<CompanyEntity> companyEntityList = companyRepository.findAll();
      return companyEntityList.stream().map(CompanyEntity-> modelMapper.map(CompanyEntity, CompanyDtoResponse.class)).toList();
    }

    @Override
    public CompanyDtoResponse getCompanyById(Long id) {
        CompanyEntity companyEntity = companyRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Company not found with id:"+id+"! Enter valid id."));
        return modelMapper.map(companyEntity, CompanyDtoResponse.class);
    }

    @Override
    public CompanyDtoResponse createCompany(CompanyDtoRequest companyDtoRequest) {
        CompanyEntity companyEntity = modelMapper.map(companyDtoRequest, CompanyEntity.class);
         companyRepository.save(companyEntity);
         return modelMapper.map(companyEntity,CompanyDtoResponse.class);
    }

    @Override
    public CompanyDtoResponse updateCompany(Long id, CompanyDtoRequest updatedCompany) {
       CompanyEntity companyEntity = companyRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Company not found with id:"+id+"! Enter valid id."));
        modelMapper.map(updatedCompany,companyEntity);
       CompanyEntity newCompany = companyRepository.save(companyEntity);
        return modelMapper.map(newCompany,CompanyDtoResponse.class);
    }

    @Override
    public CompanyDtoResponse updatePartialCompany(Long id, Map<String , Object> updates) {
        CompanyEntity companyEntity = companyRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Company not found with id:"+id+"! Enter valid id."));
        updates.forEach((field,value)->{
                switch (field) {
                    case "name":
                        companyEntity.setName(value.toString());
                        break;
                    case "website":
                        companyEntity.setWebsite(value.toString());
                        break;
                    case "location":
                        companyEntity.setLocation(value.toString());
                        break;
                    default:
                        throw new RuntimeException(new NoSuchElementException("No such field is found"));
                }
                });



       CompanyEntity updatedCompany = companyRepository.save(companyEntity);
       return modelMapper.map(updatedCompany,CompanyDtoResponse.class);
    }

    @Override
    public void  deleteCompany(Long id) {
       if(!companyRepository.existsById(id)){
           throw new IllegalArgumentException("Company of id:"+id+"doesnot exists");
       }
       companyRepository.deleteById(id);
    }
}
