package com.example.warehouseapp.repositories;

import com.example.warehouseapp.model.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
}
