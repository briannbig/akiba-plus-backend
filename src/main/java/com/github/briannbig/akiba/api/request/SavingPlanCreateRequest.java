package com.github.briannbig.akiba.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SavingPlanCreateRequest(
        @NotBlank String savingCycle,
        @NotBlank String savingStrategy,
        @NotNull double amount,
        @NotNull double target,
        @NotNull boolean reminderOn,
        @NotNull LocalDateTime startDate,
        LocalDateTime endDate
) {
}
