package com.github.briannbig.akiba.entities;

import com.github.briannbig.akiba.entities.enums.SavingCycle;
import com.github.briannbig.akiba.entities.enums.SavingStrategy;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "saving_plan")
public class SavingPlan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private SavingCycle savingCycle;
    @Enumerated(EnumType.STRING)
    SavingStrategy savingStrategy;
    double amount;
    double target;
    boolean reminderOn;
    LocalDateTime startDate;
    LocalDateTime endDate;

    public SavingPlan() {
    }

    public SavingPlan(String id, LocalDateTime createdAt, LocalDateTime updatedAt, User user, SavingCycle savingCycle, SavingStrategy savingStrategy, double amount, double target, boolean reminderOn, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, createdAt, updatedAt);
        this.user = user;
        this.savingCycle = savingCycle;
        this.savingStrategy = savingStrategy;
        this.amount = amount;
        this.target = target;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminderOn = reminderOn;
    }

    public SavingPlan(User user, SavingCycle savingCycle, SavingStrategy savingStrategy, double amount, double target, boolean reminderOn, LocalDateTime startDate, LocalDateTime endDate) {
        this(null, null, null, user, savingCycle, savingStrategy, amount, target, reminderOn, startDate, endDate);
    }

    public SavingPlan(User user, SavingCycle savingCycle, SavingStrategy savingStrategy, double amount, double target, LocalDateTime startDate, boolean reminderOn) {
        this(null, null, null, user, savingCycle, savingStrategy, amount, target, reminderOn, startDate, null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SavingCycle getSavingCycle() {
        return savingCycle;
    }

    public void setSavingCycle(SavingCycle savingCycle) {
        this.savingCycle = savingCycle;
    }

    public SavingStrategy getSavingStrategy() {
        return savingStrategy;
    }

    public void setSavingStrategy(SavingStrategy savingStrategy) {
        this.savingStrategy = savingStrategy;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public boolean isReminderOn() {
        return reminderOn;
    }

    public void setReminderOn(boolean reminderOn) {
        this.reminderOn = reminderOn;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SavingPlan{" +
                "user=" + user.getUsername() +
                ", savingCycle=" + savingCycle +
                ", savingStrategy=" + savingStrategy +
                ", amount=" + amount +
                ", target=" + target +
                ", reminderOn=" + reminderOn +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}