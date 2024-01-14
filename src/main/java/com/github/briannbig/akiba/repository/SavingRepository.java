package com.github.briannbig.akiba.repository;

import com.github.briannbig.akiba.entities.Saving;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingRepository extends JpaRepository<Saving, String> {
    List<Saving> findBySavingPlanId(String id);
}