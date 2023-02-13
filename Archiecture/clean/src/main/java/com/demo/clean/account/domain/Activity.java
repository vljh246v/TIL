package com.demo.clean.account.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Activity {

    @Getter
    ActivityId id;

    /**
     * The account that owns this activity.
     */
    @Getter
    @NonNull Account.AccountId ownerAccountId;

    /**
     * The debited account.
     */
    @Getter
    @NonNull Account.AccountId sourceAccountId;

    /**
     * The credited account.
     */
    @Getter
    @NonNull Account.AccountId targetAccountId;

    /**
     * The timestamp of the activity.
     */
    @Getter
    @NonNull LocalDateTime timestamp;

    /**
     * The money that was transferred between the accounts.
     */
    @Getter
    @NonNull Money money;

    public Activity(
            @NonNull Account.AccountId ownerAccountId,
            @NonNull Account.AccountId sourceAccountId,
            @NonNull Account.AccountId targetAccountId,
            @NonNull LocalDateTime timestamp,
            @NonNull Money money) {
        this.id = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    @Value
    public static class ActivityId {
        Long value;
    }

}
