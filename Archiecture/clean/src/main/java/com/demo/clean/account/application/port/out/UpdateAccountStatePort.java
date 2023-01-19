package com.demo.clean.account.application.port.out;

import com.demo.clean.account.domain.Account;

public interface UpdateAccountStatePort {

	void updateActivities(Account account);

}
