package io.github.isharipov.gson.adapters;

import java.math.BigDecimal;

public class SalariedEmployee extends Employee {

    private final BigDecimal weeklySalary;

    public SalariedEmployee(String id, String type, String firstName, String lastName, String ssn,
                            BigDecimal weeklySalary) {
        super(id, type, firstName, lastName, ssn);
        this.weeklySalary = weeklySalary;
    }

    public BigDecimal getWeeklySalary() {
        return weeklySalary;
    }

    @Override
    public String toString() {
        return "SalariedEmployee{" +
                "weeklySalary=" + weeklySalary +
                "} " + super.toString();
    }
}
