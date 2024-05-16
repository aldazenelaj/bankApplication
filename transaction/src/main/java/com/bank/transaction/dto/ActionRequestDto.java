package com.bank.transaction.dto;

import lombok.Data;

@Data
public class ActionRequestDto {
    private Integer bankApplicationId;
    private String action;
}
