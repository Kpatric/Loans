package com.pk.loans.model;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:27 AM
 * @project Loans
 */
public enum LoanDuration {
        WEEKLY_1(1),
        WEEKLY_2(2),
        WEEKLY_3(3),
        WEEKLY_4(4),
        MONTHLY(0);
        private final int weeks;
        LoanDuration(int weeks) {
                this.weeks = weeks;
        }

        public int getWeeks() {
                return weeks;
        }
        public boolean isWeekly() {
                return weeks > 0;
        }


}

