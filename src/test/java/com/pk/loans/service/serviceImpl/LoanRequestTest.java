package com.pk.loans.service.serviceImpl;

/**
 * @author Patrick Muriithi
 * @created 7/5/2023 - 8:09 PM
 * @project Loans
 */
import com.pk.loans.model.LoanRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanRequestTest {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    public void testLoanRequestConstraints() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setLoanDuration(-1); // Negative loan duration
        loanRequest.setStartDate(null); // Null start date
        loanRequest.setAmount(BigDecimal.ZERO); // Zero amount
        loanRequest.setLoanType(null); // Null loan type

        Set<ConstraintViolation<LoanRequest>> violations = validator.validate(loanRequest);

        assertEquals(4, violations.size());
        for (ConstraintViolation<LoanRequest> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            switch (propertyPath) {
                case "loanDuration":
                    assertEquals("Loan duration must be a positive value", message);
                    break;
                case "startDate":
                    assertEquals("Start date must not be null", message);
                    break;
                case "amount":
                    assertEquals("Amount must be a positive value", message);
                    break;
                case "loanType":
                    assertEquals("Loan type must not be null", message);
                    break;
                default:
                    // Handle additional constraints if any
                    break;
            }
        }
    }
}

