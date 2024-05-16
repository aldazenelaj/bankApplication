package com.bank.application.service;

import com.bank.application.entity.User;
import com.bank.application.exception.ExceptionErrorHandler;
import com.bank.application.request.LoginRequest;
import com.bank.application.response.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtService jwtService;


    public LoginResult login(LoginRequest loginRequest){
        LoginResult loginResponse = new LoginResult();
     //   try {
            User authenticatedUser = authenticationService.authenticate(loginRequest);

        Map<String, Object> claims = new HashMap<>();
                claims.put("role",authenticatedUser.getRole().getRolename());
            String jwtToken = jwtService.generateToken(claims,authenticatedUser);

             loginResponse.setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
            return loginResponse;
      /*  }catch(Exception e){
            loginResponse.setErrors(new ExceptionErrorHandler().handleSecurityException(e));
        }*/
      //   return loginResponse;
    }
}
