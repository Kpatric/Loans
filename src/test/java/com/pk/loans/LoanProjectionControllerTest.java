package com.pk.loans;
/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:34 AM
 * @project Loans
 */
import com.pk.loans.controller.FeeProjectionController;
import com.pk.loans.controller.InstallmentProjectionController;
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
        // Mocking the behavior of the feeProjectionCalculator
        List<String> expectedFeeProjections = Arrays.asList("2023-06-01 => 10", "2023-06-08 => 20");
        when(feeProjectionService.calculateFeeProjections(any(LoanRequest.class))).thenReturn(expectedFeeProjections);

        // Creating a sample LoanRequest
        LoanRequest loanRequest = new LoanRequest(LoanDuration.WEEKLY,LocalDate.parse("2023-06-01"),1000);


        // Invoking the endpoint
        ResponseEntity<List<String>> responseEntity = feeProjectionController.calculateFeeProjections(loanRequest);
        List<String> actualFeeProjections = responseEntity.getBody();

        // Asserting the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedFeeProjections, actualFeeProjections);

        // Asserting the result
        assertEquals(expectedFeeProjections, actualFeeProjections);
    }

    @Test
    public void testCalculateInstallmentProjections() {
        // Mocking the behavior of the installmentProjectionCalculator
        List<String> expectedInstallmentProjections = Arrays.asList("2023-06-01 => 1010", "2023-06-08 => 1020");
        when(installmentProjectionService.calculateInstallmentProjections(any(LoanRequest.class))).thenReturn(expectedInstallmentProjections);

        // Creating a sample LoanRequest
        LoanRequest loanRequest = new LoanRequest(LoanDuration.WEEKLY,LocalDate.parse("2023-06-01"),1000);

        // Invoking the endpoint
        ResponseEntity<List<String>> responseEntity = installmentProjectionController.calculateInstallmentProjections(loanRequest);
        List<String> actualInstallmentProjections = responseEntity.getBody();

        // Asserting the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedInstallmentProjections, actualInstallmentProjections);
    }
}
