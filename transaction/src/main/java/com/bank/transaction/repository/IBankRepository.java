package com.bank.transaction.repository;

import com.bank.transaction.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankRepository extends JpaRepository<BankEntity, Integer> {
}
