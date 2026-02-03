package com.example.Internship.Tracker.API.service.implementation;

import com.example.Internship.Tracker.API.dto.user_dto.UserDtoRequest;
import com.example.Internship.Tracker.API.dto.user_dto.UserDtoResponse;
import com.example.Internship.Tracker.API.entity.UserEntity;
import com.example.Internship.Tracker.API.repository.UserRepository;
import com.example.Internship.Tracker.API.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDtoResponse> getUserList() {
        List<UserEntity> userEntityList = userRepository.findAll();
        return userEntityList.stream().map(userEntity ->
                modelMapper.map(userEntity, UserDtoResponse.class)).toList();
    }

    @Override
    public UserDtoResponse getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("student not found with id :" + id));

        return modelMapper.map(userEntity, UserDtoResponse.class);   //Model mapper is used to map one model to another efficiently
    }

    @Override
    public UserDtoResponse createUsers(UserDtoRequest user) {
        UserEntity newUserEntity = modelMapper.map(user, UserEntity.class); // UserDtoRequest to UserEntity
        UserEntity userEntity = userRepository.save(newUserEntity);  // Save that Entity and local variable for further converting to return
        UserDtoResponse response = modelMapper.map(userEntity, UserDtoResponse.class);  //Entity to UserDtoResponse for my controller
        return response;  //Returning the Response of UserDto
    }

    @Override
    public void deleteUsers(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with id:" + id + "not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDtoResponse updateUsers(Long id, UserDtoRequest user) {

        UserEntity foundUser = userRepository.findById(id).orElseThrow( () ->new IllegalArgumentException("User Not found with id:"+id));
     modelMapper.map(user,foundUser);
       UserEntity updatedUser = userRepository.save(foundUser);
        return modelMapper.map(updatedUser,UserDtoResponse.class);
    }

    @Override
    public UserDtoResponse updatePartialUsers(Long id, Map<String, Object> changes) {
        UserEntity foundUser = userRepository.findById(id).orElseThrow( () ->new IllegalArgumentException("User Not found with id:"+id));

        changes.forEach((field,value)->{
            switch (field){
                case "name":foundUser.setName(value.toString());
                break;
                case "email":foundUser.setEmail(value.toString());
                break;
                default:
                    throw new RuntimeException(new NoSuchElementException("No such field is found"));
            }

        });
        UserEntity updated = userRepository.save(foundUser);
        return modelMapper.map(updated,UserDtoResponse.class);
    }
}
