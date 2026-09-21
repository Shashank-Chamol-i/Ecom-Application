package com.example.ecom_Application.controller;

import com.example.ecom_Application.dto.UserRequest;
import com.example.ecom_Application.dto.UserResponse;
import com.example.ecom_Application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/ecom")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @PostMapping("/user/create")
    public ResponseEntity<String> createUser(@RequestBody  UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userRequest));

    }
    @GetMapping("/user/gau")
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUser());

    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable String id , @RequestBody UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(userService.updateUserById(id,userRequest));

    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.deleteUserById(id));
    }
}
