package com.pk.loans.utils;

import com.pk.loans.model.LoanDuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 1:46 PM
 * @project Loans
 */
class LoanUtilityTest {

    @Test
    void getEndDate() {
    }
    @Test
    public void testGetEndDateDuration() {
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LoanDuration duration = LoanDuration.WEEKLY_3;

        var actualEndDate = LoanUtility.getEndDate(startDate, duration);
        assertEquals(LocalDate.of(2023, 6, 22), actualEndDate);

    }

    @Test
    void getNextDate() {
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LoanDuration duration = LoanDuration.WEEKLY_3;

        var actualEndDate = LoanUtility.getNextDate(startDate, duration);
        assertEquals(LocalDate.of(2023, 6, 8), actualEndDate);
    }

}