package com.example.Internship.Tracker.API.controller;

import com.example.Internship.Tracker.API.dto.user_dto.UserDtoRequest;
import com.example.Internship.Tracker.API.dto.user_dto.UserDtoResponse;
import com.example.Internship.Tracker.API.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/public/users/{id}")
    public ResponseEntity<UserDtoResponse> getUsersById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping("/public/users")
    public ResponseEntity<List<UserDtoResponse>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserList());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDtoResponse> createUsers(@RequestBody @Valid UserDtoRequest user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUsers(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable Long id){
        userService.deleteUsers(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDtoResponse> updateUsers(@PathVariable Long id,@RequestBody @Valid UserDtoRequest user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUsers(id,user));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDtoResponse> updatePartialUser(@PathVariable Long id, @RequestBody @Valid Map<String,Object> changes){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePartialUsers(id,changes));
    }

}
