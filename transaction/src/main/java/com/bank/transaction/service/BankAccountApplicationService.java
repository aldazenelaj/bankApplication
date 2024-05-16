package com.bank.transaction.service;

import com.bank.transaction.dto.ActionRequestDto;
import com.bank.transaction.dto.BankAccountApplicationDto;
import com.bank.transaction.entity.BankAccountApplicationEntity;
import com.bank.transaction.repository.IBankAccountApplicationRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing bank account applications.
 */
@Service
public class BankAccountApplicationService {
    private static final Logger log = LoggerFactory.getLogger(BankAccountApplicationService.class.getName());

    @Autowired
    public IBankAccountApplicationRepository bankAccountApplicationRepository;
    @Autowired
    public ExtractUserInformation userInformation;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BankAccountService bankAccountService;
    private static final String CLIENT_ROLE = "CLIENT";
    private static final String BANKER_ROLE = "BANKER";
    private static final String APPROVE_ACTION = "APPROVE";
    private static final List<String> ALLOWED_ACTIONS = List.of("APPROVE", "DENY");


    /**
     * Creates a new bank account application
     *
     * @param bankAccountApplicationDto the data transfer object containing bank account application details
     * @param token                     the security token used to authenticate and authorize the user making the request
     * @return {@link BankAccountApplicationDto} an instance of BankAccountApplicationDto representing the saved or updated bank account application
     * @throws AccessDeniedException if the user does not have permission to perform the operation
     * @throws Exception             if there is an issue saving the application data to the database
     */
    @Transactional
    public BankAccountApplicationDto createBankAccountApplication(BankAccountApplicationDto bankAccountApplicationDto, String token) throws Exception {
        if (!userInformation.hasPermission(token, List.of(CLIENT_ROLE))) {
            log.error("Access denied: User does not have permission required to apply for a bank account");
            throw new AccessDeniedException("User does not have the rights to access this resource");
        }

        try {
            BankAccountApplicationEntity bankAccountApplicationEntity = modelMapper.map(bankAccountApplicationDto, BankAccountApplicationEntity.class);
            bankAccountApplicationEntity.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
            if (bankAccountApplicationRepository.findBankAccountApplicationEntitiesByBankAccountTypeAndAndUserIdAndAndCurrency(bankAccountApplicationDto.getBankAccountType().getId(), bankAccountApplicationDto.getUserId(), bankAccountApplicationDto.getCurrency().getId()) != 0) {
                log.error("User already has this type of account");
                throw new Exception("User already has this type of account");
            }
            BankAccountApplicationEntity savedEntity = bankAccountApplicationRepository.save(bankAccountApplicationEntity);
            if (savedEntity == null) {
                log.error("Failed to save the bank application");
                throw new Exception("Bank application was not saved");
            }
            log.info("Bank application saved successfully");
            return modelMapper.map(savedEntity, BankAccountApplicationDto.class);
        } catch (Exception e) {
            log.error("Error saving bank application", e);
            throw e;
        }
    }

    /**
     * Approves or rejects a bank account application based on the provided action.
     * This method ensures that only authorized users (BANKER_ROLE) can approve or reject applications.
     * If approved, it creates  a new bank account
     *
     * @param actionRequestDto Body that contains The ID of the bank account application to be processed and the action to be performed
     * @param token            Security token used to authenticate and authorize the user making the request.
     * @return Boolean true if the operation was successful, false otherwise.
     * @throws AccessDeniedException    if the user does not have sufficient permissions to perform the action.
     * @throws IllegalArgumentException if the action provided is not allowed or if the application ID does not exist.
     * @throws Exception                for general failures such as database errors
     */
    @Transactional
    public Boolean approveOrRejectBankAccountApplication(ActionRequestDto actionRequestDto, String token) throws Exception {
        if (!userInformation.hasPermission(token, List.of(BANKER_ROLE))) {
            log.error("Access denied: User does not have permission required to apply for a bank account");
            throw new AccessDeniedException("User does not have the rights to access this resource");
        }
        try {
            if (!ALLOWED_ACTIONS.contains(actionRequestDto.getAction())) {
                log.error("This action it is not allowed");
                throw new Exception("This action it is not allowed");
            }

           BankAccountApplicationEntity bankAccountApplicationEntity = bankAccountApplicationRepository.getReferenceById(actionRequestDto.getBankApplicationId());
            if (bankAccountApplicationEntity == null) {
                log.error("Bank Account doesnt exists");
                throw new Exception("Bank Account doesnt exists");
            }

            bankAccountApplicationEntity.setStatus(actionRequestDto.getAction());
            BankAccountApplicationEntity updatedAccountApplication = bankAccountApplicationRepository.save(bankAccountApplicationEntity);
            if (bankAccountApplicationEntity == null ) {
                log.error("This action was not performed");
                throw new Exception("This action was not performed");
            }
            log.info("Action: " + actionRequestDto.getAction() + " was performed successfully");
            if (actionRequestDto.getAction().equals(APPROVE_ACTION)) {
                log.info("Creating bank account");
                //creating new bank account
                bankAccountService.createBankAccount(updatedAccountApplication);
            }
            return true;
        } catch (Exception e) {
            log.error("Error saving bank application", e);
            throw e;
        }
    }


}
