package com.bank.application.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ProblemDetail;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    private  String token;
    private long expiresIn;
   // private ProblemDetail errors;
    public String getToken() {
        return token;
    }

    public LoginResult setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResult setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

}
