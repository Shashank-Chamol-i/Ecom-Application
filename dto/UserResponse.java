package com.example.ecom_Application.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressDTO address;
}
