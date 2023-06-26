package com.pk.loans.service.serviceImpl;

import com.pk.loans.model.LoanDuration;
import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.FeeProjectionService;
import com.pk.loans.utils.LoanUtility;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:03 AM
 * @project Loans
 */
@Service
public class FeeProjectionServiceImpl implements FeeProjectionService {
    @Override
    public List<String> calculateFeeProjections(LoanRequest loanRequest) {
        List<String> feeProjections = new ArrayList<>();
        LocalDate startDate =loanRequest.getStartDate();
        double principal = loanRequest.getAmount();
        LocalDate currentDate = startDate;

        double interestRate;
        double serviceFeeRate;
        double maxServiceFee;

        if (loanRequest.getDuration() == LoanDuration.WEEKLY) {
            interestRate = 0.01;
            serviceFeeRate = 0.005;
            maxServiceFee = 50;
        } else if (loanRequest.getDuration() == LoanDuration.MONTHLY) {
            interestRate = 0.04;
            serviceFeeRate = 0.005;
            maxServiceFee = 100;
        } else {
            throw new IllegalArgumentException("Invalid loan duration");
        }

        while (currentDate.isBefore(LoanUtility.getEndDate(startDate, loanRequest.getDuration()))) {
            double totalFee = LoanUtility.calculateTotalFee(principal, interestRate, serviceFeeRate, maxServiceFee);
            feeProjections.add(currentDate + " => " + totalFee);
            currentDate = LoanUtility.getNextDate(currentDate, loanRequest.getDuration());
        }


        return feeProjections;
    }


}




