package com.pk.loans.service.serviceImpl;

import com.pk.loans.model.FeeProjection;
import com.pk.loans.model.InstallmentProjection;
import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.FeeProjectionService;
import com.pk.loans.service.InstallmentProjectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 8:41 PM
 * @project Loans
 */
@Service
public class InstallmentProjectionServiceImpl implements InstallmentProjectionService {

    private static final Logger logger = LoggerFactory.getLogger(InstallmentProjectionServiceImpl.class);

    @Value("${interestWeekly}")
    public String interestWeekly;

    @Value("${interestMonthly}")
    public String interestMonthly;

    @Value("${serviceCapMonthly}")
    public String serviceCapMonthly;

    @Value("${serviceCapWeekly}")
    public String serviceCapWeekly;

    @Value("${serviceFeeDuration}")
    public String serviceFeeDuration;

    @Autowired
    private FeeProjectionServiceImpl feeProjectionService;

    @Override
    public List<InstallmentProjection> calculateInstallmentProjections(LoanRequest loanApplication) {
        List<InstallmentProjection> installmentProjections = new ArrayList<>();
        LocalDate currentDate = loanApplication.getStartDate();
        BigDecimal principal = loanApplication.getAmount();
        BigDecimal installmentProjection = BigDecimal.ZERO;

        int durationInWeeks = loanApplication.getLoanDuration();
        BigDecimal principalPerWeek = calculatePrincipalPerWeek(principal, durationInWeeks);

        for (int i = 0; i < durationInWeeks; i++) {
            try {
                BigDecimal weeklyFee = calculateWeeklyFee(principal, Double.parseDouble(interestWeekly));
                BigDecimal serviceFee = calculateServiceFee(principal, Double.parseDouble(serviceCapWeekly), i);

                installmentProjection = principalPerWeek.add(weeklyFee).add(serviceFee)
                        .setScale(2, RoundingMode.HALF_UP);
                currentDate = currentDate.plusDays(loanApplication.getLoanType().getDays());
                installmentProjections.add(new InstallmentProjection(currentDate, installmentProjection));
            } catch (Exception e) {
                logger.error("An error occurred during installment projection calculation: " + e.getMessage());
            }
        }

        return installmentProjections;
    }

    private BigDecimal calculatePrincipalPerWeek(BigDecimal principal, int durationInWeeks) {
        return principal.divide(BigDecimal.valueOf(durationInWeeks), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateWeeklyFee(BigDecimal principal, double interestWeekly) {
        try {
            return feeProjectionService.calculateInterestFee(principal, new BigDecimal(interestWeekly));
        } catch (Exception e) {
            logger.error("An error occurred during weekly fee calculation: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal calculateServiceFee(BigDecimal principal, double serviceCapWeekly, int index) {
        BigDecimal serviceFee = BigDecimal.ZERO;
        if (index + 1 == Integer.parseInt(serviceFeeDuration)) {
            try {
                serviceFee = feeProjectionService.calculateServiceFee(principal, new BigDecimal(serviceCapWeekly));
            } catch (Exception e) {
                logger.error("An error occurred during service fee calculation: " + e.getMessage());
            }
        }
        return serviceFee;
    }


}
