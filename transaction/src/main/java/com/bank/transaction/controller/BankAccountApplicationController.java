package com.bank.transaction.controller;

import com.bank.transaction.dto.ActionRequestDto;
import com.bank.transaction.dto.BankAccountApplicationDto;
import com.bank.transaction.service.BankAccountApplicationService;
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

/**
 * Controller for handling bank account application requests.
 * This class provides endpoints for registering or updating bank account applications
 * and performing actions like approval or rejection on them.
 */
@RequestMapping(value = "/bank/account/application")
@RestController
public class BankAccountApplicationController {
    @Autowired
    private BankAccountApplicationService bankAccountApplicationService;
    private static final Logger log = LoggerFactory.getLogger(BankAccountApplicationController.class.getName());

    /**
     * Registers a new bank account application or updates an existing one.
     * The endpoint extracts the authentication token from the request header,
     * validates it, and processes the application.
     *
     * @param request                   the HttpServletRequest containing the HTTP request details.
     * @param bankAccountApplicationDto the DTO containing bank account application data.
     * @return ResponseEntity<?> representing the outcome of the operation.
     */
    @PostMapping
    public ResponseEntity<?> registerOrUpdate(HttpServletRequest request, @RequestBody BankAccountApplicationDto bankAccountApplicationDto) {
        // Extract the token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Missing authorization");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);

        BankAccountApplicationDto response = null;
        try {
            response = bankAccountApplicationService.createBankAccountApplication(bankAccountApplicationDto, token);
        } catch (Exception e) {
            log.error("Error during creating a bank application service", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during creating a bank application service" + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Processes an action on a bank account application such as approval or rejection.
     * The endpoint extracts the authentication token from the request header,
     * validates it, and executes the requested action.
     *
     * @param request          the HttpServletRequest containing the HTTP request details.
     * @param actionRequestDto the DTO containing the action request data.
     * @return ResponseEntity<?> representing the outcome of the action.
     */

    @PostMapping(value = "/action")
    public ResponseEntity<?> action(HttpServletRequest request, @RequestBody ActionRequestDto actionRequestDto) {
        // Extract the token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Missing authorization");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token is missing or invalid.");
        }
        String token = authorizationHeader.substring(7);
        Boolean response = false;
        try {
            response = bankAccountApplicationService.approveOrRejectBankAccountApplication(actionRequestDto, token);
        } catch (Exception e) {
            log.error("Execution interrupted with the following exception: ", e.getMessage());
          return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Execution interrupted with the following exception: " + e.getMessage());
        }
        if (response)
            return ResponseEntity.ok("Action performed successfully");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Action didnt performed ");

    }

}
