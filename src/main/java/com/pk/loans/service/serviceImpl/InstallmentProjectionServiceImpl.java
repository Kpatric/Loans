package com.pk.loans.service.serviceImpl;

import com.pk.loans.model.FeeProjection;
import com.pk.loans.model.InstallmentProjection;
import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.FeeProjectionService;
import com.pk.loans.service.InstallmentProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FeeProjectionService feeProjectionService;

    @Override
    public List<InstallmentProjection> calculateInstallmentProjections(LoanRequest loanApplication) {
        List<InstallmentProjection> installmentProjections = new ArrayList<>();

        LocalDate currentDate = loanApplication.getStartDate();
        BigDecimal principal = loanApplication.getAmount();

        BigDecimal totalFees = calculateTotalFees(loanApplication);

        BigDecimal installmentAmount = calculateInstallmentAmount(principal, totalFees, loanApplication.getLoanDuration());

        for (int i = 0; i < loanApplication.getLoanDuration(); i++) {
            installmentProjections.add(new InstallmentProjection(currentDate, installmentAmount));
            currentDate = currentDate.plusDays(loanApplication.getLoanType().getDays());
        }

        return installmentProjections;
    }

    private BigDecimal calculateTotalFees(LoanRequest loanApplication) {
        List<FeeProjection> feeProjections = feeProjectionService.calculateFeeProjections(loanApplication);
        BigDecimal totalFees = BigDecimal.ZERO;
        for (FeeProjection feeProjection : feeProjections) {
            totalFees = totalFees.add(feeProjection.getAmount());
        }
        return totalFees;
    }
    private BigDecimal calculateInstallmentAmount(BigDecimal principal, BigDecimal totalFees, int loanDuration) {
        BigDecimal totalAmount = principal.add(totalFees);
        BigDecimal installmentAmount = totalAmount.divide(BigDecimal.valueOf(loanDuration), 2, RoundingMode.HALF_UP);
        return installmentAmount;
    }


}
