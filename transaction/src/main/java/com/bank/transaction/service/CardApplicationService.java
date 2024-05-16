package com.bank.transaction.service;

import com.bank.transaction.dto.ActionCardRequestDto;
import com.bank.transaction.dto.CardApplicationDto;
import com.bank.transaction.entity.BankAccountEntity;
import com.bank.transaction.entity.CardApplicationEntity;
import com.bank.transaction.repository.IBankAccountRepository;
import com.bank.transaction.repository.ICardApplicatonRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class CardApplicationService {
    private static final Logger log = LoggerFactory.getLogger(CardApplicationService.class.getName());

    @Autowired
    private ICardApplicatonRepository cardApplicatonRepository;
    @Autowired
    private IBankAccountRepository bankAccountRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final String CLIENT_ROLE = "CLIENT";
    private static final String BANKER_ROLE = "BANKER";
    private static final String APPROVE_ACTION = "APPROVE";
    private static final List<String> ALLOWED_ACTIONS = List.of("APPROVE", "DENY");
    @Autowired
    public ExtractUserInformation userInformation;
    @Autowired
    public BankAccountService bankAccountService;

    public CardApplicationDto createCardApplication(CardApplicationDto cardApplicationDto, String token) throws AccessDeniedException {
        if (!userInformation.hasPermission(token, List.of(CLIENT_ROLE))) {
            log.error("Access denied: User does not have permission required to apply for a bank account");
            throw new AccessDeniedException("User does not have the rights to access this resource");
        }
        try {
            // Check if the card type is CREDIT and conditions for monthly salary and limit amount
            if ("CREDIT".equals(cardApplicationDto.getCardType().getType()) && cardApplicationDto.getMonthlySalary() < 500 && cardApplicationDto.getLimitAmount() <= 0) {
                log.error("Card application denied due to insufficient salary and limit amount");
                throw new Exception("Card application denied due to insufficient salary and limit amount");
            }

            // Map the DTO to the entity
            CardApplicationEntity cardApplicationEntity = modelMapper.map(cardApplicationDto, CardApplicationEntity.class);

            // Set interest based on the monthly salary
            if ("CREDIT".equals(cardApplicationDto.getCardType().getType())) {
                if (cardApplicationDto.getMonthlySalary() > 500 && cardApplicationDto.getMonthlySalary() < 1000) {
                    cardApplicationEntity.setInteres(10.0f);
                } else if (cardApplicationDto.getMonthlySalary() > 1000) {
                    cardApplicationEntity.setInteres(8.0f);
                }
            }

            // Check if the user has a CURRENT bank account if the card type is CURRENT
            if ("DEBIT".equals(cardApplicationDto.getCardType().getType())) {
                List<BankAccountEntity> bankAccounts = bankAccountRepository.findBankAccountEntityByUserId(cardApplicationDto.getUserId());
                Optional<BankAccountEntity> specificTypeAccount = bankAccounts.stream()
                        .filter(account -> "CURRENT".equals(account.getBankAccountType().getType()))
                        .findFirst();

                if (!specificTypeAccount.isPresent()) {
                    log.error("Access denied: User does not have a current account");
                    throw new Exception("Access denied: User does not have a current account");
                } else {
                    cardApplicationEntity.setBankAccount(specificTypeAccount.get());
                }
            }

            cardApplicationEntity.setStatus("REQUESTED");
            // Save the card application entity and check for errors
            CardApplicationEntity saved = cardApplicatonRepository.save(cardApplicationEntity);
            if (saved == null) {
                log.error("Card application could not be created due to error");
                throw new Exception("Card application could not be created due to error");
            }

            // Return the mapped DTO of the saved entity
            return modelMapper.map(saved, CardApplicationDto.class);
        } catch (Exception e) {
            log.error("Error creating card application", e);
            throw new RuntimeException(e);
        }
    }

    public Boolean approveOrRejectCardApplication(ActionCardRequestDto requestDto, String token) throws AccessDeniedException {
        if (!userInformation.hasPermission(token, List.of(BANKER_ROLE))) {
            log.error("Access denied: User does not have permission required to apply for a bank account");
            throw new AccessDeniedException("User does not have the rights to access this resource");
        }
        try {
            if (!ALLOWED_ACTIONS.contains(requestDto.getAction())) {
                log.error("This action it is not allowed");
                throw new Exception("This action it is not allowed");
            }

            CardApplicationEntity cardApplicationEntity = cardApplicatonRepository.getReferenceById(requestDto.getCardApplicationId());
            if (cardApplicationEntity == null) {
                log.error("Card application doesnt exists");
                throw new Exception("Card application doesnt exists");
            }
            cardApplicationEntity.setStatus(requestDto.getAction());

            CardApplicationEntity saved = cardApplicatonRepository.save(cardApplicationEntity);
            if (saved == null) {
                log.error("Card application action wasnt saved");
                throw new Exception("Card application action wasnt saved");
            }

            log.info("Action: " + requestDto.getAction() + " was performed successfully");
            if (requestDto.getAction().equals(APPROVE_ACTION)) {
                log.info("Creating bank account");
                //creating new bank account
                bankAccountService.createTechnicalBankAccount(requestDto.getBankId(), saved);
                saved.setBankAccount(modelMapper.map(bankAccountService.createTechnicalBankAccount(requestDto.getBankId(), saved), BankAccountEntity.class));
                cardApplicatonRepository.save(saved);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;

    }
}
