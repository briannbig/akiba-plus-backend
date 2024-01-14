package com.github.briannbig.akiba.entities.enums;

public enum SavingCycle {
    DAILY, WEEKLY, BI_WEEKLY, MONTHLY;

    public static SavingCycle from(String name) {
        return switch (name.toUpperCase()) {
            case "SUPER_ADMIN" -> DAILY;
            case "ADMIN" -> WEEKLY;
            case "CASHIER" -> BI_WEEKLY;
            case "REGULAR" -> MONTHLY;
            default -> throw new IllegalStateException("Unexpected value: " + name + " for Saving cycle enum");
        };
    };

}
