package com.bank.application.controller;

import com.bank.application.entity.User;
import com.bank.application.request.RegisterUserRequest;
import com.bank.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling user-related requests.
 */
@Controller
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user
     *
     * @param registerUserDto the request containing user registration details
     * @return a ResponseEntity containing the registered user
     */
    @PostMapping
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest registerUserDto) {
        User registeredUser = userService.registerOrUpdate(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Deletes a user by username.
     *
     * @param username the username of the user to be deleted
     * @return a ResponseEntity containing a success message if the user was deleted,
     * or a not found status if the user was not deleted
     */
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam("username") String username) {
        if (userService.delete(username)) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not deleted");
        }
    }

}
