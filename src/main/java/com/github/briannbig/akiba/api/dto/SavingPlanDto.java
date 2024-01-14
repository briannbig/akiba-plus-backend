package com.github.briannbig.akiba.api.dto;

import com.github.briannbig.akiba.entities.SavingPlan;

import java.time.LocalDateTime;

public record SavingPlanDto(String id, String userId, String userName, String savingCycle, String savingStrategy,
                            double amount, double target, boolean reminderOn, LocalDateTime startDate,
                            LocalDateTime endDate) {
    public static SavingPlanDto from(SavingPlan savingPlan) {
        return new SavingPlanDto(
                savingPlan.getId(),
                savingPlan.getUser().getId(),
                savingPlan.getUser().getUsername(),
                savingPlan.getSavingCycle().toString(),
                savingPlan.getSavingStrategy().toString(),
                savingPlan.getAmount(),
                savingPlan.getTarget(),
                savingPlan.isReminderOn(),
                savingPlan.getStartDate(),
                savingPlan.getEndDate()
        );
    }

}
