package com.bank.transaction.service;

import com.bank.transaction.dto.ActionRequestDto;
import com.bank.transaction.dto.BankAccountApplicationDto;
import com.bank.transaction.dto.BankTypeDto;
import com.bank.transaction.dto.CurrencyDto;
import com.bank.transaction.entity.BankAccountApplicationEntity;
import com.bank.transaction.entity.BankAccountTypeEntity;
import com.bank.transaction.entity.CurrencyEntity;
import com.bank.transaction.repository.IBankAccountApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
public class BankAccountApplicationServiceTest {

    @Mock
    private IBankAccountApplicationRepository bankAccountApplicationRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ExtractUserInformation userInformation;

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountApplicationService bankAccountApplicationService;

    private BankAccountApplicationDto applicationDto;
    private BankAccountApplicationEntity applicationEntity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        applicationDto = new BankAccountApplicationDto();
        BankTypeDto bankTypeDto = new BankTypeDto();
        bankTypeDto.setType("CURRENT");
        applicationDto.setBankAccountType(bankTypeDto);
        applicationDto.setUserId(1);
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setCurrency("USD");
        applicationDto.setCurrency(currencyDto);

        BankAccountTypeEntity bankAccountType = new BankAccountTypeEntity();
        bankAccountType.setType("CURRENT");
        applicationEntity = new BankAccountApplicationEntity();
        applicationEntity.setBankAccountType(bankAccountType);
        applicationEntity.setUserId(1);

        CurrencyEntity currencyEntity = new CurrencyEntity();
        currencyEntity.setCurrency("USD");
        applicationEntity.setCurrency(currencyEntity);
    }

    @Test
    void createBankAccountApplication_Success() throws Exception {
        when(userInformation.hasPermission(anyString(), any())).thenReturn(true);
        when(modelMapper.map(any(BankAccountApplicationDto.class), any())).thenReturn(applicationEntity);
        when(bankAccountApplicationRepository.save(any(BankAccountApplicationEntity.class))).thenReturn(applicationEntity);
        when(modelMapper.map(any(BankAccountApplicationEntity.class), any())).thenReturn(applicationDto);

        BankAccountApplicationDto result = bankAccountApplicationService.createBankAccountApplication(applicationDto, "token");

        assertNotNull(result);
        assertEquals(applicationDto.getUserId(), result.getUserId());
    }

    @Test
    void approveOrRejectBankAccountApplication_Success() throws Exception {
        ActionRequestDto actionRequestDto = new ActionRequestDto();
        actionRequestDto.setAction("APPROVE");
        actionRequestDto.setBankApplicationId(1);

        when(userInformation.hasPermission(anyString(), any())).thenReturn(true);
        when(bankAccountApplicationRepository.getReferenceById(anyInt())).thenReturn(applicationEntity);
        when(bankAccountApplicationRepository.save(any(BankAccountApplicationEntity.class))).thenReturn(applicationEntity);

        Boolean result = bankAccountApplicationService.approveOrRejectBankAccountApplication(actionRequestDto, "token");

        assertTrue(result);
    }
}
