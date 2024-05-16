package com.bank.transaction.service;

import com.bank.transaction.dto.BankAccountDto;
import com.bank.transaction.entity.*;
import com.bank.transaction.repository.IBankAccountRepository;
import com.bank.transaction.repository.IBankAccountTypeRepository;
import com.bank.transaction.repository.IBankRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Service class responsible for creating new bank accounts
 */
@Service
public class BankAccountService {
    private static final Logger log = LoggerFactory.getLogger(BankAccountService.class.getName());

    @Autowired
    IBankAccountRepository bankAccountRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    IBANGenerator ibanGenerator;
    @Autowired
    IBankAccountTypeRepository bankAccountTypeRepository;
    @Autowired
    IBankRepository bankRepository;

    /**
     * Creates a new bank account based on the provided bank account application entity.
     *
     * @param bankAccountApplicationEntity the application entity from which to derive the new bank account details
     * @return a {@link BankAccountDto} that represents the newly created bank account
     * @throws Exception if the bank account could not be created successfully, including database save errors
     */
    public BankAccountDto createBankAccount(BankAccountApplicationEntity bankAccountApplicationEntity) throws Exception {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setBankAccountType(bankAccountApplicationEntity.getBankAccountType());
        bankAccountEntity.setCurrency(bankAccountApplicationEntity.getCurrency());
        bankAccountEntity.setUserId(bankAccountApplicationEntity.getUserId());
        bankAccountEntity.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        bankAccountEntity.setBalance(0.0f);
        bankAccountEntity.setInteres(0.0f);
        bankAccountEntity.setStatus("ACTIVE");
        bankAccountEntity.setBank(bankAccountApplicationEntity.getBank());
        bankAccountEntity.setIban(ibanGenerator.generateIBAN(bankAccountApplicationEntity.getBank().getCountry().getCountryCode(), bankAccountApplicationEntity.getBank().getBankCode(), String.valueOf(bankAccountApplicationEntity.getId())));
        BankAccountEntity savedAccount = bankAccountRepository.save(bankAccountEntity);
        if (savedAccount == null) {
            log.error("Bank account not created");
            throw new Exception("Bank account not created");
        }
        log.info("Bank account was created succesfully");
        return modelMapper.map(savedAccount, BankAccountDto.class);
    }

    public BankAccountDto createTechnicalBankAccount(Integer bankId, CardApplicationEntity bankAccountApplicationEntity) throws Exception {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        BankAccountTypeEntity bankAccountType = bankAccountTypeRepository.findBankAccountTypeByType("TECHNICAL");
        bankAccountEntity.setBankAccountType(bankAccountType);
        bankAccountEntity.setUserId(bankAccountApplicationEntity.getUserId());
        bankAccountEntity.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        bankAccountEntity.setBalance(0.0f);
        bankAccountEntity.setInteres(0.0f);
        bankAccountEntity.setStatus("ACTIVE");

        BankEntity bankEntity = bankRepository.getReferenceById(bankId);
        bankAccountEntity.setBank(bankEntity);
        bankAccountEntity.setIban(ibanGenerator.generateIBAN(bankEntity.getCountry().getCountryCode(), bankEntity.getBankCode(), String.valueOf(bankAccountApplicationEntity.getId())));
        BankAccountEntity savedAccount = bankAccountRepository.save(bankAccountEntity);
        if (savedAccount == null) {
            log.error("Technical Bank account not created");
            throw new Exception("Technical Bank account not created");
        }
        log.info("Technical Bank account was created succesfully");
        return modelMapper.map(savedAccount, BankAccountDto.class);
    }

}
