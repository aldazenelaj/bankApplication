package com.bank.transaction.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtractUserInformation {
    private static final Logger log = LoggerFactory.getLogger(ExtractUserInformation.class.getName());

    @Value("${security.jwt.secret-key}")
    private String key;
    public boolean hasPermission(String token, List<String> rolePermitted) {
        if (rolePermitted.contains(this.getRoleFromUser(token))) {
            log.info("User has permission to access this functionality");
            return true;
        }
        log.info("User does not have permission to access this functionality");

        return false;
    }
    public String  getRoleFromUser(String token){
        log.info("Getting Role from token provided");
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role").toString();

    }
}
