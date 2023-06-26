package com.pk.loans.service;

import com.pk.loans.model.InstallmentProjection;
import com.pk.loans.model.LoanRequest;

import java.util.List;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:02 AM
 * @project Loans
 */
public interface InstallmentProjectionService {
    List<InstallmentProjection> calculateInstallmentProjections(LoanRequest loanRequest);
}
