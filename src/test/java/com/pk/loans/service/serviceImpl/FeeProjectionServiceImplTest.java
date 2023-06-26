package com.pk.loans.service.serviceImpl;

import com.pk.loans.model.LoanDuration;
import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.InstallmentProjectionService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 3:40 PM
 * @project Loans
 */
class FeeProjectionServiceImplTest {
    @Test
    public void testCalculateTotalFee() {
        FeeProjectionServiceImpl service = new FeeProjectionServiceImpl();
        LoanRequest loanRequest = new LoanRequest(LoanDuration.WEEKLY_3, LocalDate.parse("2023-06-01"),3000);
        var response = service.calculateFeeProjections(loanRequest);
        System.out.println(response);
    }
    @Test
    public void testcalculateInstallmentProjections() {
        InstallmentProjectionServiceImpl service = new InstallmentProjectionServiceImpl();
        LoanRequest loanRequest = new LoanRequest(LoanDuration.WEEKLY_3, LocalDate.parse("2023-06-01"),3000);
        var response = service.calculateInstallmentProjections(loanRequest);
        System.out.println(response);
    }
}