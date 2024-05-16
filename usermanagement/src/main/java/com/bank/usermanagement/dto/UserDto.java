package com.bank.usermanagement.dto;

import com.bank.usermanagement.entity.Role;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {

    private Integer id;
    private String username;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private Timestamp creationTime;
    private RoleDto role;
}
