package com.github.briannbig.akiba.repository;

import com.github.briannbig.akiba.entities.SavingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingPlanRepository extends JpaRepository<SavingPlan, String> {
    List<SavingPlan> findByUserId(String id);
}