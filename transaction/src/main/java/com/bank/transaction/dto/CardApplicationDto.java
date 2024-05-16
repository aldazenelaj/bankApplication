package com.bank.transaction.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CardApplicationDto {
    private Integer id;
    private Integer userId;
    private CardTypeDto cardType;
    private BankAccountDto bankAccount;
    private String status;
    private Timestamp createDate;
    private Float monthlySalary;
    private Float interes;
    private Float limitAmount;

}
