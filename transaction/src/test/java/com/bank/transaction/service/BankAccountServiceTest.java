package com.bank.transaction.service;

import com.bank.transaction.dto.BankAccountDto;
import com.bank.transaction.entity.*;
import com.bank.transaction.repository.IBankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @Mock
    private IBankAccountRepository bankAccountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private IBANGenerator ibanGenerator;

    @InjectMocks
    private BankAccountService bankAccountService;

    private BankAccountApplicationEntity bankAccountApplicationEntity;
    private BankAccountEntity bankAccountEntity;
    private BankAccountDto bankAccountDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        BankAccountTypeEntity bankAccountType = new BankAccountTypeEntity();
        bankAccountType.setId(1);
        bankAccountType.setType("Checking");

        CurrencyEntity currency = new CurrencyEntity();
        currency.setId(1);
        currency.setCurrency("USD");

        BankEntity bank = new BankEntity();
        bank.setId(1);
        bank.setBankCode("123456");
        bank.setCountry(new CountryEntity(1, "US", "United States"));

        bankAccountApplicationEntity = new BankAccountApplicationEntity();
        bankAccountApplicationEntity.setId(1);
        bankAccountApplicationEntity.setBankAccountType(bankAccountType);
        bankAccountApplicationEntity.setCurrency(currency);
        bankAccountApplicationEntity.setUserId(1);
        bankAccountApplicationEntity.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        bankAccountApplicationEntity.setStatus("Pending");
        bankAccountApplicationEntity.setBank(bank);

        bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(1);
        bankAccountEntity.setBankAccountType(bankAccountType);
        bankAccountEntity.setCurrency(currency);
        bankAccountEntity.setUserId(1);
        bankAccountEntity.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        bankAccountEntity.setBalance(0.0f);
        bankAccountEntity.setInteres(0.0f);
        bankAccountEntity.setStatus("ACTIVE");
        bankAccountEntity.setBank(bank);
        bankAccountEntity.setIban("US1234560001");

        bankAccountDto = new BankAccountDto();
        bankAccountDto.setId(1);
        bankAccountDto.setUserId(1);
        bankAccountDto.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        bankAccountDto.setBalance(0.0f);
        bankAccountDto.setInteres(0.0f);
        bankAccountDto.setStatus("ACTIVE");
        bankAccountDto.setIban("US1234560001");
    }

    @Test
    public void testCreateBankAccount_Success() throws Exception {
        when(ibanGenerator.generateIBAN(anyString(), anyString(), anyString())).thenReturn("US1234560001");
        when(bankAccountRepository.save(any(BankAccountEntity.class))).thenReturn(bankAccountEntity);
        when(modelMapper.map(any(BankAccountEntity.class), eq(BankAccountDto.class))).thenReturn(bankAccountDto);

        BankAccountDto result = bankAccountService.createBankAccount(bankAccountApplicationEntity);

        assertNotNull(result);
        assertEquals("US1234560001", result.getIban());
        verify(bankAccountRepository, times(1)).save(any(BankAccountEntity.class));
        verify(modelMapper, times(1)).map(any(BankAccountEntity.class), eq(BankAccountDto.class));
    }

    @Test
    public void testCreateBankAccount_Failure() {
        when(ibanGenerator.generateIBAN(anyString(), anyString(), anyString())).thenReturn("US1234560001");
        when(bankAccountRepository.save(any(BankAccountEntity.class))).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            bankAccountService.createBankAccount(bankAccountApplicationEntity);
        });

        assertEquals("Bank account not created", exception.getMessage());
        verify(bankAccountRepository, times(1)).save(any(BankAccountEntity.class));
    }
}
