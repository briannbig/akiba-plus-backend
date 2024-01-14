package com.github.briannbig.akiba.api.dto;

import com.github.briannbig.akiba.entities.Saving;

import java.time.LocalDateTime;

public record SavingDto(String Id, String savingPlanId, int occurrence, double amount, LocalDateTime timestamp) {
    public static SavingDto from(Saving saving) {
        return new SavingDto(
                saving.getId(),
                saving.getSavingPlan().getId(),
                saving.getOccurrence(),
                saving.getAmount(),
                saving.getTimestamp()
        );
    }

}
