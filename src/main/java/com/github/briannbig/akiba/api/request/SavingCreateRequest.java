package com.github.briannbig.akiba.api.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SavingCreateRequest(
        @NotBlank String savingPlanId,
        int occurrence,
        @NotNull double amount,
        @Nullable LocalDateTime timestamp

) {
}
