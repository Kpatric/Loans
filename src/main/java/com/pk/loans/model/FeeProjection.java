package com.pk.loans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 8:25 PM
 * @project Loans
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeProjection {
    private LocalDate date;
    private BigDecimal amount;

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " => " + amount;
    }
}

