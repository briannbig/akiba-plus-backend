package com.github.briannbig.akiba.entities.enums;

public enum SavingCycle {
    DAILY, WEEKLY, BI_WEEKLY, MONTHLY;

    public static SavingCycle from(String name) {
        return switch (name.toUpperCase()) {
            case "DAILY" -> DAILY;
            case "WEEKLY" -> WEEKLY;
            case "BI_WEEKLY", "BI WEEKLY" -> BI_WEEKLY;
            case "MONTHLY" -> MONTHLY;
            default -> throw new IllegalStateException("Unexpected value: " + name + " for Saving cycle enum");
        };
    };

}
