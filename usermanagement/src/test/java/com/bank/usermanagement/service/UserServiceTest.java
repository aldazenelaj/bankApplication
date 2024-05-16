package com.bank.usermanagement.service;

import com.bank.usermanagement.dto.RoleDto;
import com.bank.usermanagement.dto.UserDto;
import com.bank.usermanagement.entity.User;
import com.bank.usermanagement.external.ApiService;
import com.bank.usermanagement.repository.RoleRepository;
import com.bank.usermanagement.repository.UserRepository;
import com.bank.usermanagement.validation.CheckPermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ApiService apiService;

    @Mock
    private CheckPermission checkPermissionService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserWithPermission() throws Exception {
        UserDto input = new UserDto();
        input.setUsername("newUser");
        RoleDto roleDto = new RoleDto();
        roleDto.setRolename("ADMIN");
        input.setRole(roleDto);

        when(checkPermissionService.hasPermission(anyString(), anyList())).thenReturn(true);
        when(checkPermissionService.getRoleFromUser(anyString())).thenReturn("ADMIN");
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(modelMapper.map(any(UserDto.class), eq(User.class))).thenReturn(new User());
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(apiService.callUserPostAuthenticationServiceRest(any(UserDto.class), anyString())).thenReturn(true);

        assertDoesNotThrow(() -> userService.register(input, "TOKEN"));
    }

    @Test
    void testRegisterUserPermissionDenied() {
        UserDto input = new UserDto();
        input.setUsername("newUser");
        RoleDto roleDto = new RoleDto();
        roleDto.setRolename("ADMIN");
        input.setRole(roleDto);

        when(checkPermissionService.hasPermission(anyString(), anyList())).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> userService.register(input, "token"));
        assertEquals("User not permitted to do this action", exception.getMessage());
    }

    @Test
    void testDeleteUserSuccessful() {
        String username = "existingUser";
        String token = "validToken";

        when(apiService.callUserDeleteAuthenticationServiceRest(username, token)).thenReturn(true);
        when(userRepository.deleteUserByUsername(username)).thenReturn(1);

        assertTrue(userService.delete(username, token));
    }

    @Test
    void testDeleteUserFailure() {
        String username = "nonExistingUser";
        String token = "validToken";

        when(apiService.callUserDeleteAuthenticationServiceRest(username, token)).thenReturn(false);

        assertFalse(userService.delete(username, token));
    }


    @Test
    void testRegisterUserAlreadyExists() throws Exception {
        UserDto input = new UserDto();
        input.setUsername("existingUser");

        when(checkPermissionService.hasPermission(anyString(), anyList())).thenReturn(true);
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        Exception exception = assertThrows(Exception.class, () -> userService.register(input, "token"));
        assertEquals("User with username: existingUser already exists", exception.getMessage());
    }



}

