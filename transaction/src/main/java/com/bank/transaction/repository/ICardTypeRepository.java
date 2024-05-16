package com.bank.transaction.repository;

import com.bank.transaction.entity.CardTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardTypeRepository extends JpaRepository<CardTypeEntity,Integer> {
}
