package org.example.entity;
import jakarta.validation.constraints.*;

import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Company name cannot be blank!")
    @Size(max = 20, message = "Company name has to be with up to 20 characters!")
    @Pattern(regexp = "^([A-Z]).*", message = "Company name has to start with capital letter!")
    @Column(name = "name", nullable = false)
    private String name;

    @PastOrPresent(message = "Foundation date cannot be in the future!")
    @Column(name = "foundation_date")
    private LocalDate foundationDate;

    @Positive
    @Column(name = "init_capital", nullable = false)
    private double initialCapital;

    @Column(name="revenue")
    private double revenue;
    @Column(name="income")
    private double income;
    @Column(name="expenses")
    private double expenses;

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public void setRevenue() {
        this.revenue = this.getInitialCapital() + this.getIncome() - this.getExpenses();
    }

    public double getRevenue() {
        return this.revenue;
    }



    public Company() {
    }

    public Company(String name, LocalDate foundationDate, double initialCapital) {
        this.name = name;
        this.foundationDate = foundationDate;
        this.initialCapital = initialCapital;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }

    public double getInitialCapital() {
        return initialCapital;
    }

    public void setInitialCapital(double initialCapital) {
        this.initialCapital = initialCapital;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", foundationDate=" + foundationDate +
                ", initialCapital=" + initialCapital +
                ", revenue=" + revenue +
                ", income=" + income +
                ", expenses=" + expenses +
                '}';
    }
}
