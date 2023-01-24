package com.demo.clean.account.domain;

import static com.demo.clean.common.AccountTestData.defaultAccount;
import static com.demo.clean.common.ActivityTestData.defaultActivity;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.demo.clean.account.domain.Account.AccountId;

class AccountTest {

    @Test
    void withdrawalSucceeds(){
        AccountId accountId = new AccountId(1L);

        Account account = defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(555L))
                .withActivityWindow(new ActivityWindow(
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(999L)).build(),
                        defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(1L)).build()
                )).build();

        boolean success = account.withdraw(Money.of(555L), new AccountId(99L));

        assertThat(success).isTrue();
        assertThat(account.getActivityWindow().getActivities().size()).isEqualTo(3);
        assertThat(account.calculateBalance()).isEqualTo(Money.of(1000L));
    }

}