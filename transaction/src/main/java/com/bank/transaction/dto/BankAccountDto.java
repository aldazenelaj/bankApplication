package com.bank.transaction.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BankAccountDto {
    private Integer id;
    private Integer userId;
    private BankTypeDto bankAccountType;
    private CurrencyDto currency;
    private String status;
    private String iban;
    private Float interes;
    private Float balance;
    private Timestamp createDate;

}
