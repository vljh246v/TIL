package com.example.gof.structural.adapter.after.security;

import org.junit.jupiter.api.Test;

import com.example.gof.structural.adapter.after.AccountService;
import com.example.gof.structural.adapter.after.AccountUserDetailsService;

class LoginHandlerTest {

    @Test
    void login() {
        LoginHandler loginHandler = new LoginHandler(new AccountUserDetailsService(new AccountService()));
        String jaehyun = loginHandler.login("jaehyun", "jaehyun");
        System.out.println(jaehyun);
    }

}