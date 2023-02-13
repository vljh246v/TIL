package com.demo.clean.account.application.service;

import java.time.LocalDateTime;

import com.demo.clean.account.application.port.in.GetAccountBalanceQuery;
import com.demo.clean.account.application.port.out.LoadAccountPort;
import com.demo.clean.account.domain.Account.AccountId;
import com.demo.clean.account.domain.Money;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
                              .calculateBalance();
    }
}
