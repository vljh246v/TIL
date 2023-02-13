package com.demo.clean.account.application.port.out;

import com.demo.clean.account.domain.Account;

public interface AccountLock {

	void lockAccount(Account.AccountId accountId);

	void releaseAccount(Account.AccountId accountId);

}
