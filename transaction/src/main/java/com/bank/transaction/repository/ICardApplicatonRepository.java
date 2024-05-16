package com.bank.transaction.repository;

import com.bank.transaction.entity.CardApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardApplicatonRepository extends JpaRepository<CardApplicationEntity,Integer> {
}
