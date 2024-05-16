package com.bank.application.service;

import com.bank.application.entity.Role;
import com.bank.application.entity.User;
import com.bank.application.repository.RoleRepository;
import com.bank.application.repository.UserRepository;
import com.bank.application.request.RegisterUserRequest;
import com.bank.application.request.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerOrUpdateTest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setId(1);
        request.setUsername("newuser");
        request.setEmail("newuser@example.com");
        request.setPassword("password123");
        request.setName("New");
        request.setLastName("User");
        RoleDto roleDTO = new RoleDto();
        roleDTO.setRolename("TEST");
        request.setRole(roleDTO);

        Role role = new Role();
        role.setRolename("TEST");


        User mockUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findRoleByRolename("TEST")).thenReturn(role);

        User result = userService.registerOrUpdate(request);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("password123");
        verify(roleRepository).findRoleByRolename("TEST");
    }

    @Test
    void deleteUserTest_Success() {
        when(userRepository.deleteUserByUsername("newuser")).thenReturn(1); // Assume deleteUserByUsername returns the number of records deleted

        Boolean result = userService.delete("newuser");

        assertTrue(result);
        verify(userRepository).deleteUserByUsername("newuser");
    }

    @Test
    void deleteUserTest_Failure() {
        when(userRepository.deleteUserByUsername("newuser")).thenReturn(0); // No user found to delete

        Boolean result = userService.delete("newuser");

        assertFalse(result);
        verify(userRepository).deleteUserByUsername("newuser");
    }
}
