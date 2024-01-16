package com.github.briannbig.akiba.api.dto;

import com.github.briannbig.akiba.entities.SavingPlan;

import java.time.LocalDateTime;
import java.util.List;

public record SavingPlanDto(String id, String userId, String userName, String savingCycle, String savingStrategy,
                            String goal,
                            double amount, double target, double currentBalance, boolean reminderOn,
                            LocalDateTime startDate, LocalDateTime endDate, List<SavingDto> savings) {
    public static SavingPlanDto from(SavingPlan savingPlan) {
        return new SavingPlanDto(
                savingPlan.getId(),
                savingPlan.getUser().getId(),
                savingPlan.getUser().getUsername(),
                savingPlan.getSavingCycle().toString(),
                savingPlan.getSavingStrategy().toString(),
                savingPlan.getGoal(),
                savingPlan.getAmount(),
                savingPlan.getTarget(),
                savingPlan.getCurrentBalance(),
                savingPlan.isReminderOn(),
                savingPlan.getStartDate(),
                savingPlan.getEndDate(),
                savingPlan.getSavings().stream().map(SavingDto::from).toList()
        );
    }

}
