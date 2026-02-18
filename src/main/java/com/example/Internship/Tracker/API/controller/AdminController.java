package com.example.Internship.Tracker.API.controller;

import com.example.Internship.Tracker.API.dto.OnBoardRecruiterRequestDto;
import com.example.Internship.Tracker.API.dto.user_dto.UserDtoResponse;
import com.example.Internship.Tracker.API.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/onBoardNewRecuriter")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDtoResponse> onBoardNewRecruiter(@RequestBody OnBoardRecruiterRequestDto onBoardRecruiterRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.onBoardNewRecruiter(onBoardRecruiterRequestDto));
    }
}
