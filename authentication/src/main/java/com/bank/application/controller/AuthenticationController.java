package com.bank.application.controller;

import com.bank.application.exception.ExceptionErrorHandler;
import com.bank.application.request.LoginRequest;
import com.bank.application.response.LoginResult;
import com.bank.application.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for handling authentication requests.
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private LoginService loginService;

    /**
     * Authenticates a user based on the provided login request.
     *
     * @param loginUserDto the login request containing username and password
     * @return a ResponseEntity containing the login result or an error response
     */
    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody LoginRequest loginUserDto) {
        try {
            LoginResult loginResult = loginService.login(loginUserDto);
            return ResponseEntity.ok(loginResult);
        } catch (Exception e) {
            ProblemDetail problemDetail = new ExceptionErrorHandler().handleSecurityException(e);
            return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
        }
    }
}
