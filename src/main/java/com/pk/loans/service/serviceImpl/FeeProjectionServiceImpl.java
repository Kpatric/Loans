package com.pk.loans.service.serviceImpl;

import com.pk.loans.model.FeeProjection;
import com.pk.loans.model.LoanDuration;
import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.FeeProjectionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<FeeProjection> calculateFeeProjections(LoanRequest loanApplication) {
        List<FeeProjection> feeProjections = new ArrayList<>();

        LocalDate currentDate = loanApplication.getStartDate();
        BigDecimal principal = loanApplication.getAmount();
        LoanDuration loanDuration = loanApplication.getLoanType();

        BigDecimal interestRate = getInterestRate(loanDuration);
        BigDecimal serviceFeeCap = getServiceFeeCap(loanDuration);
        int serviceFeeFrequency = getServiceFeeFrequency(loanDuration);

        for (int i = 0; i < loanApplication.getLoanDuration(); i++) {
            BigDecimal interestFee = calculateInterestFee(principal, interestRate);
            feeProjections.add(new FeeProjection(currentDate, interestFee));

            if (isServiceFeeApplicable(i, serviceFeeFrequency)) {
                BigDecimal serviceFee = calculateServiceFee(principal, serviceFeeCap);
                feeProjections.add(new FeeProjection(currentDate, serviceFee));
            }

            currentDate = currentDate.plusDays(loanDuration.getDays());
        }

        return feeProjections;
    }

    private BigDecimal getInterestRate(LoanDuration loanDuration) {
        return (loanDuration == LoanDuration.WEEKLY) ? BigDecimal.valueOf(0.01) : BigDecimal.valueOf(0.04);
    }

    private BigDecimal getServiceFeeCap(LoanDuration loanDuration) {
        return (loanDuration == LoanDuration.WEEKLY) ? BigDecimal.valueOf(50) : BigDecimal.valueOf(100);
    }

    private int getServiceFeeFrequency(LoanDuration loanDuration) {
        return (loanDuration == LoanDuration.WEEKLY) ? 2 : 3;
    }

    private BigDecimal calculateInterestFee(BigDecimal principal, BigDecimal interestRate) {
        return principal.multiply(interestRate);
    }

    private boolean isServiceFeeApplicable(int installmentIndex, int serviceFeeFrequency) {
        return (installmentIndex + 1) % serviceFeeFrequency == 0;
    }

    private BigDecimal calculateServiceFee(BigDecimal principal, BigDecimal serviceFeeCap) {
        BigDecimal serviceFee = principal.multiply(BigDecimal.valueOf(0.005));
        return (serviceFee.compareTo(serviceFeeCap) > 0) ? serviceFeeCap : serviceFee;
    }

}





