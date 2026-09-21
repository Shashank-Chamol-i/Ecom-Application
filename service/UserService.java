package com.example.ecom_Application.service;

import com.example.ecom_Application.dto.AddressDTO;
import com.example.ecom_Application.dto.UserRequest;
import com.example.ecom_Application.dto.UserResponse;
import com.example.ecom_Application.entity.Address;
import com.example.ecom_Application.entity.User;
import com.example.ecom_Application.repository.AddressRepository;
import com.example.ecom_Application.repository.UserRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserService {
private final UserRepository userRepository;

    public String createUser(UserRequest userRequest) {
        Address address = Address.builder()
                .street(userRequest.getAddress().getStreet())
                .city(userRequest.getAddress().getCity())
                .state(userRequest.getAddress().getState())
                .country(userRequest.getAddress().getCountry())
                .zipcode(userRequest.getAddress().getZipcode())
                .build();
       User user = User.builder()
               .firstName(userRequest.getFirstName())
               .lastName(userRequest.getLastName())
               .email(userRequest.getEmail())
               .phone(userRequest.getPhone())
               .address(address)
               .build();
       userRepository.save(user);
       return "User Created Successfully : ";
    }

    public List<UserResponse> getAllUser(){
        return userRepository.findAll().stream()
                .map(this::mapUserToResponse)
                .collect(Collectors.toList());
    }
    public UserResponse getUserById(String id){
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("No Such User Exist : "));
        return mapUserToResponse(user);
    }

    public  UserResponse updateUserById(String id,UserRequest userRequest){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("No Such User Exist : "));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        Address address = user.getAddress();
        address.setStreet(userRequest.getAddress().getStreet());
        address.setCity(userRequest.getAddress().getCity());
        address.setState(userRequest.getAddress().getState());
        address.setCountry(userRequest.getAddress().getCountry());
        address.setZipcode(userRequest.getAddress().getZipcode());
        user.setAddress(address);
        userRepository.save(user);
        return mapUserToResponse(user);
    }

    public String deleteUserById(String id){
       User user =  userRepository.findById(id).orElseThrow(()->new RuntimeException("No Such User exist to Delete : "));
       userRepository.delete(user);
       return "User Deleted Successfully : ";
    }







    public UserResponse mapUserToResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        if(user.getAddress()!=null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDTO);
        }
        return userResponse;
    }
}
