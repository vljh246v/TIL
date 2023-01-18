package com.demo.clean.account.application.port.in;

import com.demo.clean.account.domain.Account.AccountId;
import com.demo.clean.account.domain.Money;

public interface GetAccountBalanceQuery {
    Money getAccountBalance(AccountId accountId);
}
