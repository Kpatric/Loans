package com.pk.loans.utils;

import com.pk.loans.model.LoanConstants;
import com.pk.loans.model.LoanDuration;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:15 AM
 * @project Loans
 */
@UtilityClass
public class LoanUtility {
    public LocalDate getEndDate(LocalDate startDate, LoanDuration duration) {
        if (duration == LoanDuration.WEEKLY) {
            return startDate.plusWeeks(LoanConstants.WEEKS_IN_MONTH);
        } else if (duration == LoanDuration.MONTHLY) {
            return startDate.plusMonths(LoanConstants.MONTHS_IN_YEAR);
        } else {
            throw new IllegalArgumentException("Invalid loan duration");
        }
    }

    public LocalDate getNextDate(LocalDate currentDate, LoanDuration duration) {
        if (duration == LoanDuration.WEEKLY) {
            return currentDate.plusWeeks(1);
        } else if (duration == LoanDuration.MONTHLY) {
            return currentDate.plusMonths(1);
        } else {
            throw new IllegalArgumentException("Invalid loan duration");
        }
    }

    public double calculateServiceFee(double principal, double serviceFeeRate, double maxServiceFee) {
        return (principal * serviceFeeRate) > maxServiceFee ? maxServiceFee : (principal * serviceFeeRate);
    }

    public double calculateTotalFee(double principal, double interestRate, double serviceFeeRate, double maxServiceFee) {
        double interest = principal * interestRate;
        double serviceFee = LoanUtility.calculateServiceFee(principal, serviceFeeRate, maxServiceFee);
        return interest + serviceFee;
    }
}

