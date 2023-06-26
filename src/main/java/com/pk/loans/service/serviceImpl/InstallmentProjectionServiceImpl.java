package com.pk.loans.service.serviceImpl;

import com.pk.loans.model.LoanDuration;
import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.InstallmentProjectionService;
import com.pk.loans.utils.LoanUtility;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:04 AM
 * @project Loans
 */
@Service
public class InstallmentProjectionServiceImpl implements InstallmentProjectionService {
    @Override
    public List<String> calculateInstallmentProjections(LoanRequest loanRequest) {
        List<String> installmentProjections = new ArrayList<>();
        LocalDate startDate = loanRequest.getStartDate();
        double principal = loanRequest.getAmount();
        LocalDate currentDate = startDate;

        double interestRate;
        double serviceFeeRate;
        double maxServiceFee;
        int installmentCount;

        if (loanRequest.getDuration() == LoanDuration.WEEKLY) {
            interestRate = 0.01;
            serviceFeeRate = 0.005;
            maxServiceFee = 50;
            installmentCount = 4;
        } else if (loanRequest.getDuration() == LoanDuration.MONTHLY) {
            interestRate = 0.04;
            serviceFeeRate = 0.005;
            maxServiceFee = 100;
            installmentCount = 12;
        } else {
            throw new IllegalArgumentException("Invalid loan duration");
        }

        while (currentDate.isBefore(LoanUtility.getEndDate(startDate, loanRequest.getDuration()))) {
            double installmentAmount = calculateInstallmentAmount(principal, interestRate, serviceFeeRate, maxServiceFee, installmentCount);
            installmentProjections.add(currentDate + " => " + installmentAmount);
            currentDate = LoanUtility.getNextDate(currentDate, loanRequest.getDuration());
        }

        return installmentProjections;
    }
    private double calculateInstallmentAmount(double principal, double interestRate, double serviceFeeRate, double maxServiceFee, int installmentCount) {
        double totalFee = LoanUtility.calculateTotalFee(principal, interestRate, serviceFeeRate, maxServiceFee);
        return (principal / installmentCount) + totalFee;
    }
}

