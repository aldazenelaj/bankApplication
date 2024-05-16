package com.bank.transaction.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BankAccountApplicationDto {
    private Integer id;
    private Integer userId;
    private BankTypeDto bankAccountType;
    private CurrencyDto currency;
    private String status;
    private Timestamp createDate;
    private BankDto bank;

}
