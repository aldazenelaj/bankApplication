package com.bank.transaction.controller;

import com.bank.transaction.dto.ActionCardRequestDto;
import com.bank.transaction.dto.ActionRequestDto;
import com.bank.transaction.dto.CardApplicationDto;
import com.bank.transaction.service.CardApplicationService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/bank/card/application")
@RestController
public class CardApplicationController {
    private static final Logger log = LoggerFactory.getLogger(CardApplicationController.class.getName());

    @Autowired
    private CardApplicationService cardApplicationService;

    @PostMapping
    public ResponseEntity<?> registerOrUpdate(HttpServletRequest request, @RequestBody CardApplicationDto cardApplicationDto) {
        // Extract the token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Missing authorization");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);
        CardApplicationDto response = null;
        try {
            response = cardApplicationService.createCardApplication(cardApplicationDto, token);
        } catch (Exception e) {
            log.error("Error during creating a card application service", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during creating a card application service" + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/action")
    public ResponseEntity<?> action(HttpServletRequest request, @RequestBody ActionCardRequestDto actionRequestDto) {
        // Extract the token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Missing authorization");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);
        Boolean response = false;
        try {
            response = cardApplicationService.approveOrRejectCardApplication(actionRequestDto, token);
        } catch (Exception e) {
            log.error("Execution interrupted with the following exception: ", e.getMessage());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Execution interrupted with the following exception: " + e.getMessage());
        }
        if (response)
            return ResponseEntity.ok("Action performed successfully");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Action didnt performed ");

    }

}
