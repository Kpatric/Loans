package com.pk.loans.utils;

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
        if (duration == LoanDuration.WEEKLY_1 || duration == LoanDuration.WEEKLY_2 ||
                duration == LoanDuration.WEEKLY_3 || duration == LoanDuration.WEEKLY_4) {
            int weeksToAdd = duration.ordinal() + 1;
            return startDate.plusWeeks(weeksToAdd);
        } else if (duration == LoanDuration.MONTHLY) {
            return startDate.plusMonths(1);
        } else {
            throw new IllegalArgumentException("Invalid loan duration");
        }
    }

    public LocalDate getNextDate(LocalDate currentDate, LoanDuration duration) {
        if (duration == LoanDuration.WEEKLY_1 || duration == LoanDuration.WEEKLY_2 ||
                duration == LoanDuration.WEEKLY_3 || duration == LoanDuration.WEEKLY_4) {
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

    public double calculateTotalFee(double principal, double interestRate, double serviceFeeRate, double maxServiceFee,int noOfweeks) {
        double interest = principal * interestRate;
        double serviceFee = 0.0;

        if (noOfweeks % 2 == 0) {
            serviceFee = Math.min((principal * serviceFeeRate), maxServiceFee);
        }

        return interest + serviceFee;
    }
    public double calculateTotalInstallment(double principal, double interestRate, double serviceFeeRate, double maxServiceFee, int installmentCount) {
        double interest = principal * interestRate;
        double serviceFee = 0.0;

        if (installmentCount % 2 == 0) {
            serviceFee = Math.min((principal * serviceFeeRate), maxServiceFee);
        }

        return interest + serviceFee;
    }



}

