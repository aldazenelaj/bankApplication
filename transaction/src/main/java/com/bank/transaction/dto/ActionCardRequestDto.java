package com.bank.transaction.dto;

import lombok.Data;

@Data
public class ActionCardRequestDto {
    private Integer cardApplicationId;
    private String action;
    private Integer bankId;
}
