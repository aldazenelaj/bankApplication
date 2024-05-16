package com.bank.transaction.repository;

import com.bank.transaction.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyRepository extends JpaRepository<CurrencyEntity,Integer> {
}
