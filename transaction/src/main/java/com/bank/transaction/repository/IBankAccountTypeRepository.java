package com.bank.transaction.repository;

import com.bank.transaction.entity.BankAccountTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankAccountTypeRepository extends JpaRepository<BankAccountTypeEntity, Integer> {

    public BankAccountTypeEntity findBankAccountTypeByType(String type);
}
