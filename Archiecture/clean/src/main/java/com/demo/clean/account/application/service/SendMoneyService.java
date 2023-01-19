package com.demo.clean.account.application.service;

import org.springframework.stereotype.Service;

import com.demo.clean.account.application.port.in.SendMoneyCommand;
import com.demo.clean.account.application.port.in.SendMoneyUseCase;


@Service
public class SendMoneyService implements SendMoneyUseCase {
    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        return false;
    }
}
