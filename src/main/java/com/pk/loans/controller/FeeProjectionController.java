package com.pk.loans.controller;

import com.pk.loans.model.LoanRequest;
import com.pk.loans.service.FeeProjectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Patrick Muriithi
 * @created 6/26/2023 - 11:05 AM
 * @project Loans
 */
@RestController
@RequestMapping("/api")
public class FeeProjectionController {

    private final FeeProjectionService feeProjectionService;

    public FeeProjectionController(FeeProjectionService feeProjectionService) {
        this.feeProjectionService = feeProjectionService;
    }

    @PostMapping("/fee-projections")
    public ResponseEntity<List<String>> calculateFeeProjections(@RequestBody LoanRequest loanRequest) {
        List<String> feeProjections = feeProjectionService.calculateFeeProjections(loanRequest);
        return ResponseEntity.ok(feeProjections);
    }
}

