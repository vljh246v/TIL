package com.demo.clean.common;

import com.demo.clean.account.domain.Account;
import com.demo.clean.account.domain.Account.AccountId;
import com.demo.clean.account.domain.ActivityWindow;
import com.demo.clean.account.domain.Money;

public class AccountTestData {

	public static AccountBuilder defaultAccount() {
		return new AccountBuilder()
				.withAccountId(new AccountId(42L))
				.withBaselineBalance(Money.of(999L))
				.withActivityWindow(new ActivityWindow(
						ActivityTestData.defaultActivity().build(),
						ActivityTestData.defaultActivity().build()));
	}


	public static class AccountBuilder {

		private AccountId accountId;
		private Money baselineBalance;
		private ActivityWindow activityWindow;

		public AccountBuilder withAccountId(AccountId accountId) {
			this.accountId = accountId;
			return this;
		}

		public AccountBuilder withBaselineBalance(Money baselineBalance) {
			this.baselineBalance = baselineBalance;
			return this;
		}

		public AccountBuilder withActivityWindow(ActivityWindow activityWindow) {
			this.activityWindow = activityWindow;
			return this;
		}

		public Account build() {
			return Account.withId(this.accountId, this.baselineBalance, this.activityWindow);
		}

	}


}
