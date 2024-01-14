package com.github.briannbig.akiba.service;

import com.github.briannbig.akiba.api.request.SavingCreateRequest;
import com.github.briannbig.akiba.api.request.SavingPlanCreateRequest;
import com.github.briannbig.akiba.entities.Saving;
import com.github.briannbig.akiba.entities.SavingPlan;
import com.github.briannbig.akiba.entities.enums.SavingCycle;
import com.github.briannbig.akiba.entities.enums.SavingStrategy;
import com.github.briannbig.akiba.repository.SavingPlanRepository;
import com.github.briannbig.akiba.repository.SavingRepository;
import com.github.briannbig.akiba.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavingsService {

    private final SavingPlanRepository savingPlanRepository;
    private final SavingRepository savingRepository;
    private final UserRepository userRepository;

    public SavingsService(SavingPlanRepository savingPlanRepository, SavingRepository savingRepository, UserRepository userRepository) {
        this.savingPlanRepository = savingPlanRepository;
        this.savingRepository = savingRepository;
        this.userRepository = userRepository;
    }

    public Optional<SavingPlan> addSavingPlan(SavingPlanCreateRequest request) throws Exception {
        var optionalUser = userRepository.findById(request.userId());

        if (optionalUser.isEmpty()) {
            throw new Exception("User with given id could not be found");
        }

        var savingPlan = new SavingPlan(optionalUser.get(), SavingCycle.from(request.savingCycle()), SavingStrategy.from(request.savingStrategy()),
                request.amount(), request.target(), request.startDate(), request.reminderOn());

        savingPlan = savingPlanRepository.saveAndFlush(savingPlan);

        return Optional.of(savingPlan);
    }

    public List<SavingPlan> allSavingPlans() {
        return savingPlanRepository.findAll();
    }

    public Optional<Saving> addSaving(SavingCreateRequest request) throws Exception {
        var optionalSavingPlan = savingPlanRepository.findById(request.savingPlanId());

        if (optionalSavingPlan.isEmpty()) {
            throw new Exception("Saving plan for given id could not be found");
        }

        var saving = new Saving(optionalSavingPlan.get(), request.occurrence(), request.amount());
        saving = savingRepository.saveAndFlush(saving);

        return Optional.of(saving);
    }

    public List<Saving> getPlanSavings(String id) {
        return savingRepository.findBySavingPlanId(id);
    }

}
