package io.github.isharipov.gson.adapters;

import java.math.BigDecimal;

public class CommissionEmployee extends Employee {

    private final BigDecimal grossSales;
    private final BigDecimal commissionRate;

    public CommissionEmployee(
            String id, String type, String firstName, String lastName, String ssn,
            BigDecimal grossSales, BigDecimal commissionRate) {
        super(id, type, firstName, lastName, ssn);
        this.grossSales = grossSales;
        this.commissionRate = commissionRate;
    }

    public BigDecimal getGrossSales() {
        return grossSales;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    @Override
    public String toString() {
        return "CommissionEmployee{" +
                "grossSales=" + grossSales +
                ", commissionRate=" + commissionRate +
                "} " + super.toString();
    }
}
