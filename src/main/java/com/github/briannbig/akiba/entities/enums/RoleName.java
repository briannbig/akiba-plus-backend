package com.github.briannbig.akiba.entities.enums;

public enum RoleName {
    SUPER_ADMIN, ADMIN, CASHIER, REGULAR, DEVELOPER;

    public static RoleName from(String name) {
        return switch (name.toUpperCase()) {
            case "SUPER_ADMIN" -> SUPER_ADMIN;
            case "ADMIN" -> ADMIN;
            case "CASHIER" -> CASHIER;
            case "REGULAR" -> REGULAR;
            case "DEVELOPER" -> DEVELOPER;
            default -> throw new IllegalStateException("Unexpected value: " + name + " for ROLE");
        };
    };

}
