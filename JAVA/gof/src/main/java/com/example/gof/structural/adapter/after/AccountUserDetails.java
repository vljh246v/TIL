package com.example.gof.structural.adapter.after;

import com.example.gof.structural.adapter.after.security.UserDetails;

import lombok.RequiredArgsConstructor;

// Adapter
@RequiredArgsConstructor
public class AccountUserDetails implements UserDetails {

    private final Account account;

    @Override
    public String getUsername() {
        return account.getName();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }
}
