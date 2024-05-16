package com.bank.usermanagement.service;

import com.bank.usermanagement.dto.RoleDto;
import com.bank.usermanagement.dto.UserDto;
import com.bank.usermanagement.entity.User;
import com.bank.usermanagement.external.ApiService;
import com.bank.usermanagement.repository.RoleRepository;
import com.bank.usermanagement.repository.UserRepository;
import com.bank.usermanagement.validation.CheckPermission;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing users.
 */
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApiService apiService;
    @Autowired
    private CheckPermission checkPermissionService;

    /**
     * Registers a new user or updates an existing one.
     *
     * @param input the user data
     * @param token the security token used for authentication and authorization
     * @return the registered or updated user data
     * @throws Exception if the user does not have permission or if there are errors during the registration or update process
     */
    public UserDto register(UserDto input, String token) throws Exception {
        if (!checkPermissionService.hasPermission(token, List.of("ADMIN", "BANKER"))) {
            log.error("User not permitted to do this action");
            throw new Exception("User not permitted to do this action");
        }

        //  User userInput = modelMapper.map(input, User.class);
        User existing = userRepository.findByUsername(input.getUsername()).orElse(null);
        if (existing != null) {
            log.error("User with username: " + input.getUsername() + " already exists");
            throw new Exception("User with username: " + input.getUsername() + " already exists");

        }
        String userRole = checkPermissionService.getRoleFromUser(token);
        if (userRole.equals("ADMIN")) {
            log.info("User has role ADMIN so creating a user BANKER...");
            input.setRole(modelMapper.map(roleRepository.findRoleByRolename("BANKER"), RoleDto.class));
        } else if (userRole.equals("BANKER")) {
            log.info("User has role BANKER so creating a user CLIENT...");
            input.setRole(modelMapper.map(roleRepository.findRoleByRolename("CLIENT"), RoleDto.class));
        }

        User savedUser = userRepository.save(modelMapper.map(input, User.class));
        if (savedUser != null) {
            log.info("User with username: " + savedUser.getUsername() + " its saved in usermanagement service");
            if (!apiService.callUserPostAuthenticationServiceRest(input, token)) {
                log.info("User with username: " + input.getUsername() + " couldnt be saved in authentication service");
                log.info("Rollbacking the user from usermanagement");
                userRepository.deleteUserByUsername(savedUser.getUsername());
            } else {
                log.info("User with username: " + input.getUsername() + " was saved in authentication service");
            }
            return modelMapper.map(savedUser, UserDto.class);
        }

        log.error("User not saved");
        throw new Exception("User not saved");
    }

    /**
     * Updates an existing user.
     *
     * @param input the user data
     * @param token the security token used for authentication and authorization
     * @return the updated user data
     * @throws Exception if the user does not have permission or if there are errors during the update process
     */
    public UserDto update(UserDto input, String token) throws Exception {
        if (!checkPermissionService.hasPermission(token, List.of("ADMIN", "BANKER"))) {
            log.error("User not permitted to do this action");
            throw new Exception("User not permitted to do this action");
        }
        User existing = userRepository.findByUsername(input.getUsername()).orElse(null);
        if (existing != null && !existing.getId().equals(input.getId())) {
            log.error("User with username: " + input.getUsername() + " already exits");
            throw new Exception("User with username: " + input.getUsername() + " already exits");

        }

        String userRole = checkPermissionService.getRoleFromUser(token);
        if (userRole.equals("ADMIN")) {
            log.info("User has role ADMIN so updating a user BANKER...");
            input.setRole(modelMapper.map(roleRepository.findRoleByRolename("BANKER"), RoleDto.class));
        } else if (userRole.equals("BANKER")) {
            log.info("User has role BANKER so updating a user CLIENT...");
            input.setRole(modelMapper.map(roleRepository.findRoleByRolename("CLIENT"), RoleDto.class));
        }
        User savedUser = userRepository.save(modelMapper.map(input, User.class));
        if (savedUser != null) {
            log.info("User with username: " + savedUser.getUsername() + " its saved in usermanagement service");
            if (!apiService.callUserPostAuthenticationServiceRest(input, token)) {
                log.info("User with username: " + input.getUsername() + " couldnt be saved in authentication service");
                log.info("Rollbacking the user from usermanagement");
                userRepository.deleteUserByUsername(savedUser.getUsername());
            } else {
                log.info("User with username: " + input.getUsername() + " was saved in authentication service");
            }
            return modelMapper.map(savedUser, UserDto.class);
        }

        log.error("User not saved");
        throw new Exception("User not saved");
    }

    /**
     * Deletes a user by username.
     *
     * @param username the username of the user to be deleted
     * @param token    the security token used for authentication and authorization
     * @return true if the user was deleted successfully, false otherwise
     */
    public Boolean delete(String username, String token) {
        if (!apiService.callUserDeleteAuthenticationServiceRest(username, token)) {
            log.error("User wasnt deleted or doesnt exists");
            return false;
        }
        if (userRepository.deleteUserByUsername(username) != 0) {
            log.error("User deleted");

            return true;
        }

        return false;
    }


}



