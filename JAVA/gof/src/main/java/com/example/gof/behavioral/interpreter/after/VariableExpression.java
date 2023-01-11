package com.example.gof.behavioral.interpreter.after;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VariableExpression implements PostfixExpression {

    private final Character variable;

    @Override
    public int interpret(Map<Character, Integer> context) {
        return context.get(variable);
    }
}
