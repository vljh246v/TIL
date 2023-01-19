package com.demo.clean.account.application.port.out;

import java.time.LocalDateTime;

import com.demo.clean.account.domain.Account;
import com.demo.clean.account.domain.Account.AccountId;

public interface LoadAccountPort {

	Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}
