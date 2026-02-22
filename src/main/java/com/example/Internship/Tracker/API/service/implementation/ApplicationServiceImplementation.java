package com.example.Internship.Tracker.API.service.implementation;

import com.example.Internship.Tracker.API.config.type.ApplicationStatus;
import com.example.Internship.Tracker.API.dto.application_dto.ApplicationDtoRequest;
import com.example.Internship.Tracker.API.dto.application_dto.ApplicationDtoResponse;
import com.example.Internship.Tracker.API.dto.application_dto.ApplicationStatusCountDtoResponse;
import com.example.Internship.Tracker.API.entity.ApplicationEntity;
import com.example.Internship.Tracker.API.repository.ApplicationRepository;
import com.example.Internship.Tracker.API.repository.UserRepository;
import com.example.Internship.Tracker.API.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImplementation implements ApplicationService {

    private final ModelMapper modelMapper;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('APPLICATION_READ') and (@authz.isOwner(#userId) or @authz.isAdmin())")
    @Override
    public List<ApplicationDtoResponse> getApplications(Long userId){
        List<ApplicationEntity> applicationEntityList =applicationRepository.findByUserId(userId);

        return applicationEntityList.stream().map(applicationEntity ->
            modelMapper.map(applicationEntity,ApplicationDtoResponse.class)
        ).toList();
    }

    @PreAuthorize("hasAuthority('APPLICATION_DELETE') and (@authz.isApplicationOwner(#id) or @authz.isAdmin())")
    @Override
    public void deleteApplication(Long id){
        if(!applicationRepository.existsById(id)){
            throw new IllegalArgumentException("application not found with id :" + id);
        }
        applicationRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('APPLICATION_WRITE')")
    @Override
    public ApplicationDtoResponse createApplication(ApplicationDtoRequest application) {
       ApplicationEntity applicationEntity =  modelMapper.map(application,ApplicationEntity.class);
      ApplicationEntity saved = applicationRepository.save(applicationEntity);
        return modelMapper.map(saved,ApplicationDtoResponse.class);
    }

    @PreAuthorize("hasAuthority('APPLICATION_WRITE') and (@authz.isApplicationOwner(#userId) or @authz.isAdmin())")
    @Override
    public ApplicationDtoResponse updateApplication(Long id,ApplicationDtoRequest application) {

        ApplicationEntity applicationEntity = applicationRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Application with id "+id+" not found!"));
        modelMapper.map(application,applicationEntity);
        ApplicationEntity saved = applicationRepository.save(applicationEntity);
        return modelMapper.map(saved,ApplicationDtoResponse.class);
    }

    @PreAuthorize("hasAuthority('APPLICATION_WRITE') and (@authz.isApplicationOwner(#userId) or @authz.isAdmin())")
    @Transactional
    @Override
    public ApplicationDtoResponse updateStaus(Long id, ApplicationStatus newStatus){
        ApplicationEntity application = applicationRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Application not found with id:"+id));
       ApplicationStatus currentStatus = application.getApplicationStatus();

      if(!currentStatus.canTransitionTo(newStatus)){
          throw new IllegalStateException("Status cannot be update from "+currentStatus+" to "+newStatus);
      }

      application.setApplicationStatus(newStatus);
      application.setLastUpdated(LocalDateTime.now());

      return modelMapper.map(application,ApplicationDtoResponse.class);
    }

    @PreAuthorize("hasAuthority('APPLICATION_READ')  and (@authz.isApplicationOwner(#userId) or @authz.isAdmin())")
    @Override
    public List<ApplicationStatusCountDtoResponse> countAllApplicationStatus(Long userId){
        return applicationRepository.groupApplications(userId);
    }

    @PreAuthorize("hasAuthority('APPLICATION_READ')  and (@authz.isApplicationOwner(#userId) or @authz.isAdmin())")
    @Override
    @Transactional
    public Page<ApplicationDtoResponse> applicationsOrderByAppliedDate(Long userId, Pageable pageable){

      if(!userRepository.existsById(userId)){
          throw new IllegalArgumentException("User not found with id: "+userId);
      }

      int maxPageSize = 100;
      int requestedPageSize = pageable.getPageSize();

      if(requestedPageSize>maxPageSize){
          pageable = PageRequest.of(pageable.getPageNumber(),maxPageSize,pageable.getSort());
      }

      if(!pageable.getSort().isSorted()){
          pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by(Sort.Direction.DESC,"appliedDate"));
      }

      Page<ApplicationEntity> applicationEntityPage = applicationRepository.findByUserIdOrderByAppliedDateDesc(userId, pageable);
      return applicationEntityPage.map(applicationEntity -> modelMapper.map(applicationEntity,ApplicationDtoResponse.class));
    }




}
