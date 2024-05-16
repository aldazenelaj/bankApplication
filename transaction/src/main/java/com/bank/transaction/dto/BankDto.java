package com.bank.transaction.dto;


import lombok.Data;

@Data
public class BankDto {
    private Integer id;
    private String bankCode;
    private CountryDto country;
}
