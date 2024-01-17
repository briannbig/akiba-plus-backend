package com.github.briannbig.akiba.entities.enums;

public enum RoleName {
    SUPER_ADMIN, ADMIN, CUSTOMER, DEVELOPER;

    public static RoleName from(String name) {
        return switch (name.toUpperCase()) {
            case "ADMIN" -> ADMIN;
            case "CUSTOMER" -> CUSTOMER;
            case "DEVELOPER" -> DEVELOPER;
            default -> throw new IllegalStateException("Unexpected value: " + name + " for ROLE");
        };
    };

}
