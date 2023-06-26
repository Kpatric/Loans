package com.pk.loans.service;

import com.pk.loans.model.FeeProjection;
import com.pk.loans.model.LoanRequest;

import java.util.List;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:00 AM
 * @project Loans
 */
public interface FeeProjectionService {
    List<FeeProjection> calculateFeeProjections(LoanRequest loanRequest);
}
