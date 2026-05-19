package com.tdd.clean_architecture25.loan.internal.repository;

import com.tdd.clean_architecture25.loan.internal.model.LoanOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoanOrderRepository extends JpaRepository<LoanOrder, UUID> {
}
