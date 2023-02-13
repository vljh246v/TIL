package com.demo.clean.account.application.service;

import org.springframework.stereotype.Component;

import com.demo.clean.account.application.port.out.AccountLock;
import com.demo.clean.account.domain.Account.AccountId;

@Component
class NoOpAccountLock implements AccountLock {

	@Override
	public void lockAccount(AccountId accountId) {
		// do nothing
	}

	@Override
	public void releaseAccount(AccountId accountId) {
		// do nothing
	}

}
