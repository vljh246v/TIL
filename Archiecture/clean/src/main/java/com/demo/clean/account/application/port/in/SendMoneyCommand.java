package com.demo.clean.account.application.port.in;

import com.demo.clean.account.domain.Account.AccountId;
import com.demo.clean.account.domain.Money;
import com.demo.clean.common.SelfValidating;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final AccountId sourceAccountId;
    @NotNull
    private final AccountId targetAccountId;
    @NotNull
    private final Money money;

    public SendMoneyCommand(
            final AccountId sourceAccountId,
            final AccountId targetAccountId,
            final Money money
    ) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }

}
