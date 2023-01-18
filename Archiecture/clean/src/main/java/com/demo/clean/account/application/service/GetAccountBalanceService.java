package com.demo.clean.account.application.service;

import com.demo.clean.account.application.port.in.GetAccountBalanceQuery;
import com.demo.clean.account.domain.Account.AccountId;
import com.demo.clean.account.domain.Money;

public class GetAccountBalanceService implements GetAccountBalanceQuery {
    @Override
    public Money getAccountBalance(AccountId accountId) {
        return null;
    }
}
