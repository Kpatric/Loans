package com.pk.loans.model;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:27 AM
 * @project Loans
 */
public enum LoanDuration {
    WEEKLY(7),
    MONTHLY(30);

    private final int days;

    LoanDuration(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }


}

