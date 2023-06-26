package com.pk.loans.service.serviceImpl;

import com.pk.loans.model.LoanDuration;
import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.InstallmentProjectionService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 3:40 PM
 * @project Loans
 */
class FeeProjectionServiceImplTest {
    @Test
    public void testCalculateTotalFee() {
        List<String> expectedFeeProjections = Arrays.asList("2023-06-01 => 10", "2023-06-08 => 20");
        FeeProjectionServiceImpl service = new FeeProjectionServiceImpl();
        LoanRequest loanRequest = new LoanRequest(LoanDuration.WEEKLY_1, LocalDate.parse("2023-06-01"), 1000);
        var response = service.calculateFeeProjections(loanRequest);
        assertEquals(expectedFeeProjections, response);
        System.out.println(response);
    }

    @Test
    public void testcalculateInstallmentProjections() {
        List<String> expectedFeeProjections = Arrays.asList("2023-06-01 => 1010.0");
        InstallmentProjectionServiceImpl service = new InstallmentProjectionServiceImpl();
        LoanRequest loanRequest = new LoanRequest(LoanDuration.WEEKLY_1, LocalDate.parse("2023-06-01"), 1000);
        var response = service.calculateInstallmentProjections(loanRequest);
        assertEquals(expectedFeeProjections, response);
    }
}