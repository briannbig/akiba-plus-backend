package com.github.briannbig.akiba.entities;

import com.github.briannbig.akiba.entities.enums.SavingCycle;
import com.github.briannbig.akiba.entities.enums.SavingStrategy;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "saving_plan")
public class SavingPlan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private SavingCycle savingCycle;
    @Enumerated(EnumType.STRING)
    SavingStrategy savingStrategy;
    String goal;

    double amount;
    double target;
    boolean reminderOn;
    LocalDateTime startDate;
    LocalDateTime endDate;
    @OneToMany(mappedBy = "savingPlan", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Saving> savings;


    public SavingPlan() {
    }

    public SavingPlan(
            String id, LocalDateTime createdAt, LocalDateTime updatedAt, User user, SavingCycle savingCycle,
            SavingStrategy savingStrategy, String goal, double amount, double target,
            boolean reminderOn, LocalDateTime startDate, LocalDateTime endDate, List<Saving> savings) {
        super(id, createdAt, updatedAt);
        this.user = user;
        this.savingCycle = savingCycle;
        this.savingStrategy = savingStrategy;
        this.goal = goal;
        this.amount = amount;
        this.target = target;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminderOn = reminderOn;
        this.savings = savings;

    }

    public SavingPlan(User user, SavingCycle savingCycle, SavingStrategy savingStrategy, String goal, double amount, double target, boolean reminderOn, LocalDateTime startDate, LocalDateTime endDate) {
        this(null, null, null, user, savingCycle, savingStrategy, goal, amount, target, reminderOn, startDate, endDate, null);
    }

    public SavingPlan(User user, SavingCycle savingCycle, SavingStrategy savingStrategy, String goal, double amount, double target, LocalDateTime startDate, boolean reminderOn) {
        this(user, savingCycle, savingStrategy, goal, amount, target, reminderOn, startDate, null);
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

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getGoal() {
        return goal;
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

    public List<Saving> getSavings() {
        return savings;
    }

    public void setSavings(List<Saving> savings) {
        this.savings = savings;
    }

    public double getCurrentBalance() {
        return savings.stream().mapToDouble(Saving::getAmount).sum();
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