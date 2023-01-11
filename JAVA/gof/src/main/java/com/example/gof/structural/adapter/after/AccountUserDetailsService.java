package com.example.gof.structural.adapter.after;

import com.example.gof.structural.adapter.after.security.UserDetails;
import com.example.gof.structural.adapter.after.security.UserDetailsService;

import lombok.RequiredArgsConstructor;

// Adapter
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {

    final AccountService accountService;

    @Override
    public UserDetails loadUser(String username) {
        Account accountByUsername = accountService.findAccountByUsername(username);
        return new AccountUserDetails(accountByUsername);
    }
}
