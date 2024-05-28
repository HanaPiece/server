package com.project.hana_piece.account.controller;

import com.project.hana_piece.account.dto.AccountGetResponse;
import com.project.hana_piece.account.dto.AccountTypeRegRequest;
import com.project.hana_piece.account.dto.AccountUpsertResponse;
import com.project.hana_piece.account.service.AccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountUpsertResponse> saveAccount(@AuthenticationPrincipal Long userId) {
        AccountUpsertResponse response = accountService.saveAccount(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/account-type-reg")
    public ResponseEntity<Void> registerAccountType(@AuthenticationPrincipal Long userId, @RequestBody AccountTypeRegRequest accountTypeRegRequest) {
        accountService.registerAccountType(userId, accountTypeRegRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/checking")
    public ResponseEntity<List<AccountGetResponse>> findCheckingAccountList(@AuthenticationPrincipal Long userId) {
        List<AccountGetResponse> response = accountService.findCheckingAccountList(userId);
        return ResponseEntity.ok(response);
    }
}
