package com.pk.loans;
/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:34 AM
 * @project Loans
 */

import com.pk.loans.controller.FeeProjectionController;
import com.pk.loans.controller.InstallmentProjectionController;
import com.pk.loans.model.FeeProjection;
import com.pk.loans.model.InstallmentProjection;
import com.pk.loans.model.LoanDuration;
import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.FeeProjectionService;
import com.pk.loans.service.InstallmentProjectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanProjectionControllerTest {
    @Mock
    private FeeProjectionService feeProjectionService;

    @Mock
    private InstallmentProjectionService installmentProjectionService;

    @InjectMocks
    private FeeProjectionController feeProjectionController;

    @InjectMocks
    private InstallmentProjectionController installmentProjectionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateFeeProjections() {
        BigDecimal number = new BigDecimal("3000");
        // Mocking the behavior of the feeProjectionCalculator
        List<FeeProjection> expectedFeeProjections = List.of(
                new FeeProjection(LocalDate.of(2023, 6, 1), BigDecimal.valueOf(30)),
                new FeeProjection(LocalDate.of(2023, 6, 8), BigDecimal.valueOf(30)),
                new FeeProjection(LocalDate.of(2023, 6, 8), BigDecimal.valueOf(15)),
                new FeeProjection(LocalDate.of(2023, 6, 15), BigDecimal.valueOf(30))
        );
        when(feeProjectionService.calculateFeeProjections(any(LoanRequest.class))).thenReturn(expectedFeeProjections);

        // Creating a sample LoanRequest
        LoanRequest loanRequest = new LoanRequest(3,LocalDate.parse("2023-06-01"),number,LoanDuration.WEEKLY);


         //Invoking the endpoint
        List<FeeProjection> actualFeeProjections = feeProjectionController.calculateFeeProjections(loanRequest);
        assertEquals(expectedFeeProjections, actualFeeProjections);

    }

    @Test
    public void testCalculateInstallmentProjections() {
        BigDecimal number = new BigDecimal("1000");
        List<InstallmentProjection> expectedInstallmentProjections = List.of(
                new InstallmentProjection(LocalDate.of(2023, 6, 1), BigDecimal.valueOf(1010))
        );
        when(installmentProjectionService.calculateInstallmentProjections(any(LoanRequest.class))).thenReturn(expectedInstallmentProjections);

        // Creating a sample LoanRequest
        LoanRequest loanRequest = new LoanRequest(1,LocalDate.parse("2023-06-01"),number,LoanDuration.WEEKLY);

        // Invoking the endpoint
        List<InstallmentProjection> actualProjections = installmentProjectionController.calculateInstallmentProjections(loanRequest);


        // Asserting the result
        assertEquals(expectedInstallmentProjections, actualProjections);

    }
}
