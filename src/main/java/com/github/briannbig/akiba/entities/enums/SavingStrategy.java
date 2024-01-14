package com.github.briannbig.akiba.entities.enums;

public enum SavingStrategy {
    NORMAL, AGGRESSIVE;
    public static  SavingStrategy from(String name) {
        return  switch (name.toUpperCase()){
            case "NORMAL" -> NORMAL;
            case "AGGRESSIVE" -> AGGRESSIVE;
            default -> throw new IllegalStateException("Unexpected value: " + name + " for Saving Strategy enum");
        };
    }
}
