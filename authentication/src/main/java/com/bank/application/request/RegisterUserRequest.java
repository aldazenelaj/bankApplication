package com.bank.application.request;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String username;
    private RoleDto role;
}
