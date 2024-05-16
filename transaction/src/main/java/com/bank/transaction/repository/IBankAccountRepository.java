package com.bank.transaction.repository;

import com.bank.transaction.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBankAccountRepository extends JpaRepository<BankAccountEntity, Integer> {
    public List<BankAccountEntity> findBankAccountEntityByUserId(Integer userId);
}
