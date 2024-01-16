package com.github.briannbig.akiba.service;

import com.github.briannbig.akiba.UserContext;
import com.github.briannbig.akiba.api.request.SavingCreateRequest;
import com.github.briannbig.akiba.api.request.SavingPlanCreateRequest;
import com.github.briannbig.akiba.entities.Saving;
import com.github.briannbig.akiba.entities.SavingPlan;
import com.github.briannbig.akiba.entities.enums.SavingCycle;
import com.github.briannbig.akiba.entities.enums.SavingStrategy;
import com.github.briannbig.akiba.repository.SavingPlanRepository;
import com.github.briannbig.akiba.repository.SavingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavingsService {

    private final SavingPlanRepository savingPlanRepository;
    private final SavingRepository savingRepository;

    public SavingsService(SavingPlanRepository savingPlanRepository, SavingRepository savingRepository) {
        this.savingPlanRepository = savingPlanRepository;
        this.savingRepository = savingRepository;
    }

    public Optional<SavingPlan> addSavingPlan(SavingPlanCreateRequest request) throws Exception {

        var existingPlansCount = savingPlanRepository.findByUserId(UserContext.getUserId()).size();

        if (existingPlansCount >= 3) {
            throw new Exception("User already reached max allowed saving plans");
        }

        var savingPlan = new SavingPlan(UserContext.getUser(), SavingCycle.from(request.savingCycle()), SavingStrategy.from(request.savingStrategy()),
                request.goal(), request.amount(), request.target(), request.startDate(), request.reminderOn());

        savingPlan = savingPlanRepository.saveAndFlush(savingPlan);

        return Optional.of(savingPlan);
    }

    public List<SavingPlan> allSavingPlans() {
        List<String> roles = UserContext.getUser().getRoles().stream().map(r -> r.getRoleName().name()).toList();
        if (roles.contains("ADMIN")) {
            return savingPlanRepository.findAll();
        } else {
            return savingPlanRepository.findByUserId(UserContext.getUser().getId());
        }

    }

    public Optional<SavingPlan> getPlanById(String id) {
        return savingPlanRepository.findById(id);
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
