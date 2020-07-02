package io.github.isharipov.gson.adapters;

import java.math.BigDecimal;

public class HourlyEmployee extends Employee {

    private final BigDecimal wage;
    private final int hours;

    public HourlyEmployee(
            String id, String type, String firstName, String lastName, String ssn,
            BigDecimal wage, int hours
    ) {
        super(id, type, firstName, lastName, ssn);
        this.wage = wage;
        this.hours = hours;
    }

    public BigDecimal getWage() {
        return wage;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" +
                "wage=" + wage +
                ", hours=" + hours +
                "} " + super.toString();
    }
}
