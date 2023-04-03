package com.example.warehouseapp.repositories;

import com.example.warehouseapp.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
