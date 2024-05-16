package com.bank.usermanagement.validation;

import com.bank.usermanagement.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckPermission {
    private static final Logger log = LoggerFactory.getLogger(CheckPermission.class.getName());

    @Value("${security.jwt.secret-key}")
    private String key;

    public boolean hasPermission(String token, List<String> rolePermitted) {
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
        if (claims != null && rolePermitted.contains(claims.get("role").toString())) {
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
