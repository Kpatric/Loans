package com.pk.loans.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 10:58 AM
 * @project Loans
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRequest {
    private int loanDuration;
    private LocalDate startDate;
    private BigDecimal amount;
    private LoanDuration loanType;
}

