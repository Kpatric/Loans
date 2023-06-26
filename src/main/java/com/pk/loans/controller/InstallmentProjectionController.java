package com.pk.loans.controller;

import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.InstallmentProjectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:07 AM
 * @project Loans
 */
@RestController
@RequestMapping("/api")
public class InstallmentProjectionController {

    private final InstallmentProjectionService installmentProjectionService;

    public InstallmentProjectionController(InstallmentProjectionService installmentProjectionService) {
        this.installmentProjectionService = installmentProjectionService;
    }

    @PostMapping("/installment-projections")
    public ResponseEntity<String> calculateInstallmentProjections(@RequestBody LoanRequest loanRequest) {
        String installmentProjections = installmentProjectionService.calculateInstallmentProjections(loanRequest).toString();
        return ResponseEntity.ok(installmentProjections);
    }
}

