package com.tdd.clean_architecture25.inventory.internal.repository;

import com.tdd.clean_architecture25.inventory.internal.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PartRepository extends JpaRepository<Part, UUID> {
}
