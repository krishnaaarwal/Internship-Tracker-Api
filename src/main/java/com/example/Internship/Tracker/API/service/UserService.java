package com.example.Internship.Tracker.API.service;

import com.example.Internship.Tracker.API.dto.user_dto.UserDtoRequest;
import com.example.Internship.Tracker.API.dto.user_dto.UserDtoResponse;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<UserDtoResponse> getUserList();
    UserDtoResponse getUserById(Long id);
    UserDtoResponse createUsers(UserDtoRequest user);
    void deleteUsers(Long id);
    UserDtoResponse updateUsers(Long id, UserDtoRequest user);
    UserDtoResponse updatePartialUsers(Long id, Map<String, Object> changes);
}
