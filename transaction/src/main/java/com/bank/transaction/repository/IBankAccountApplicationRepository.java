package com.bank.transaction.repository;

import com.bank.transaction.entity.BankAccountApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IBankAccountApplicationRepository extends JpaRepository<BankAccountApplicationEntity,Integer> {
    @Query(value = "select e.id from BankAccountApplicationEntity e where e.bankAccountType.id = :bankAccountType and e.userId = :userId and e.currency.id = :currencyId")
    Integer  findBankAccountApplicationEntitiesByBankAccountTypeAndAndUserIdAndAndCurrency(Integer bankAccountType,Integer userId, Integer currencyId);

    @Query(value = "select e.id from BankAccountApplicationEntity e where e.id = :id")
    public Integer findBankAccountApplicationEntitiesById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update BankAccountApplicationEntity e set e.status = :status where e.id = :id")
   Integer update(String status, Integer id);




}
