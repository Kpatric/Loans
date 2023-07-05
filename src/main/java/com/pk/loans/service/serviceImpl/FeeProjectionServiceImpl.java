package com.pk.loans.service.serviceImpl;

import com.pk.loans.model.FeeProjection;
import com.pk.loans.model.LoanDuration;
import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.FeeProjectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    private static final Logger logger = LoggerFactory.getLogger(FeeProjectionServiceImpl.class);
    @Value("${interestWeekly}")
    public String interestWeekly;

    @Value("${interestMonthly}")
    public String interestMonthly;

    @Value("${serviceCapMonthly}")
    public String serviceCapMonthly;

    @Value("${serviceCapWeekly}")
    public String serviceCapWeekly;

    @Value("${serviceFee}")
    public String serviceFees;

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
        try {
            return (loanDuration == LoanDuration.WEEKLY) ? new BigDecimal(interestWeekly) : new BigDecimal(interestMonthly);
        } catch (NumberFormatException e) {
            logger.error("Error parsing interest rate: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid interest rate format");
        }
    }

    private BigDecimal getServiceFeeCap(LoanDuration loanDuration) {
        try {
            return (loanDuration == LoanDuration.WEEKLY) ? new BigDecimal(serviceCapWeekly) : new BigDecimal(serviceCapMonthly);
        } catch (NumberFormatException e) {
            logger.error("Error parsing service fee cap: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid service fee cap format");
        }
    }

    private int getServiceFeeFrequency(LoanDuration loanDuration) {
        return (loanDuration == LoanDuration.WEEKLY) ? 2 : 3;
    }

    public BigDecimal calculateInterestFee(BigDecimal principal, BigDecimal interestRate) {
        return principal.multiply(interestRate);
    }

    private boolean isServiceFeeApplicable(int installmentIndex, int serviceFeeFrequency) {
        return (installmentIndex + 1) % serviceFeeFrequency == 0;
    }

    public BigDecimal calculateServiceFee(BigDecimal principal, BigDecimal serviceFeeCap) {
        try {
            BigDecimal serviceFee = principal.multiply(new BigDecimal(serviceFees));
            return (serviceFee.compareTo(serviceFeeCap) > 0) ? serviceFeeCap : serviceFee;
        } catch (NumberFormatException e) {
            logger.error("Error parsing service fees: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid service fees format");
        }
    }

}





