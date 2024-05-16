package com.bank.usermanagement.controller;

import com.bank.usermanagement.dto.UserDto;
import com.bank.usermanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("*")
@RequestMapping(value = "/user")
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> registerOrUpdate(HttpServletRequest request, @RequestBody UserDto userDto) {
        // Extract the token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Missing authorization");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid.");
        }

        String token = authorizationHeader.substring(7);
        UserDto response;
        try {
            response = userService.register(userDto, token);
        } catch (Exception e) {
            log.error("Failed to register or update due to an error:+ e.getMessage()");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register or update due to an error: " + e.getMessage());
        }

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            log.error("Registration or update failed with no specific error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration or update failed with no specific error.");
        }
    }


    @PutMapping
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody UserDto userDto) {
        // Extract the token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Missing authorization");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid.");
        }

        String token = authorizationHeader.substring(7);
        UserDto response;
        try {
            response = userService.update(userDto, token);
        } catch (Exception e) {
            log.error("Failed to update due to an error:+ e.getMessage()");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update due to an error: " + e.getMessage());
        }

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            log.error("Update failed with no specific error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Update failed with no specific error.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestParam("username") String username) {
        // Extract the token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Missing authorization");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid.");
        }

        String token = authorizationHeader.substring(7);
        if (userService.delete(username, token))
            return ResponseEntity.ok("User: " + username + " is deleted");

        return ResponseEntity.ok("User: " + username + "wasn't deleted or doesnt exists");
    }
}




