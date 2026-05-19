package com.tdd.clean_architecture25.alert.internal.repository;

import com.tdd.clean_architecture25.alert.internal.model.StockAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockAlertRepository extends JpaRepository<StockAlert, UUID> {
}
