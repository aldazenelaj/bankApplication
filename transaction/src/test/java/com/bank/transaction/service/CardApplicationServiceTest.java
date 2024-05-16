package com.bank.transaction.service;

import com.bank.transaction.dto.ActionCardRequestDto;
import com.bank.transaction.dto.CardApplicationDto;
import com.bank.transaction.dto.CardTypeDto;
import com.bank.transaction.entity.CardApplicationEntity;
import com.bank.transaction.entity.CardTypeEntity;
import com.bank.transaction.repository.IBankAccountRepository;
import com.bank.transaction.repository.ICardApplicatonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardApplicationServiceTest {

    @Mock
    private ICardApplicatonRepository cardApplicatonRepository;

    @Mock
    private IBankAccountRepository bankAccountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ExtractUserInformation userInformation;

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private CardApplicationService cardApplicationService;

    private CardApplicationDto cardApplicationDto;
    private CardApplicationEntity cardApplicationEntity;

    @BeforeEach
    void setUp() {
        cardApplicationDto = new CardApplicationDto();
        CardTypeDto cardTypeDto = new CardTypeDto();
        cardTypeDto.setType("CREDIT");
        cardApplicationDto.setCardType(cardTypeDto);
        cardApplicationDto.setMonthlySalary(600.0F);
        cardApplicationDto.setLimitAmount(1000.0F);

        cardApplicationEntity = new CardApplicationEntity();
        CardTypeEntity cardTypeEntity = new CardTypeEntity();
        cardTypeEntity.setType("CREDIT");
        cardApplicationEntity.setCardType(cardTypeEntity);
        cardApplicationEntity.setMonthlySalary(600.0f);
        cardApplicationEntity.setLimitAmount(1000.0f);
    }

    @Test
    void createCardApplication_Success() throws AccessDeniedException {
        when(userInformation.hasPermission(anyString(), anyList())).thenReturn(true);
        when(modelMapper.map(any(CardApplicationDto.class), any())).thenReturn(cardApplicationEntity);
        when(cardApplicatonRepository.save(any(CardApplicationEntity.class))).thenReturn(cardApplicationEntity);
        when(modelMapper.map(any(CardApplicationEntity.class), any())).thenReturn(cardApplicationDto);

        CardApplicationDto result = cardApplicationService.createCardApplication(cardApplicationDto, "token");

        assertNotNull(result);
        assertEquals(cardApplicationDto.getMonthlySalary(), result.getMonthlySalary());
    }

    @Test
    void approveOrRejectCardApplication_Success() throws AccessDeniedException {
        ActionCardRequestDto requestDto = new ActionCardRequestDto();
        requestDto.setAction("APPROVE");
        requestDto.setCardApplicationId(1);

        when(userInformation.hasPermission(anyString(), anyList())).thenReturn(true);
        when(cardApplicatonRepository.getReferenceById(anyInt())).thenReturn(cardApplicationEntity);
        when(cardApplicatonRepository.save(any(CardApplicationEntity.class))).thenReturn(cardApplicationEntity);

        Boolean result = cardApplicationService.approveOrRejectCardApplication(requestDto, "token");

        assertTrue(result);
    }
}

