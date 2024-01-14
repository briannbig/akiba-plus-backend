package com.github.briannbig.akiba.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity(name = "saving")
public class Saving extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "saving_plan_id")
    SavingPlan savingPlan;
    int occurrence;
    double amount;
    LocalDateTime timestamp;

    public Saving() {
    }

    public Saving(String id, LocalDateTime createdAt, LocalDateTime updatedAt, SavingPlan savingPlan, int occurrence, double amount, LocalDateTime timestamp) {
        super(id, createdAt, updatedAt);
        this.savingPlan = savingPlan;
        this.occurrence = occurrence;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    protected void prePersist() {
        super.prePersist();
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    public Saving(SavingPlan savingPlan, int occurrence, double amount, LocalDateTime timestamp) {
        this(null, null, null, savingPlan, occurrence, amount, timestamp);
    }

    public Saving(SavingPlan savingPlan, int occurrence, double amount) {
        this(null, null, null, savingPlan, occurrence, amount, null);
    }


    public SavingPlan getSavingPlan() {
        return savingPlan;
    }

    public void setSavingPlan(SavingPlan savingPlan) {
        this.savingPlan = savingPlan;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Saving{" +
                "savingPlanId=" + savingPlan.getId() +
                ", occurrence=" + occurrence +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
