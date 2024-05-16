package com.bank.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card_application")
public class CardApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_type_id", nullable = false)
    private CardTypeEntity cardType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;
    private String status;
    private Timestamp createDate;
    private Float monthlySalary;
    private Float interes;
    private Float limitAmount;
}
