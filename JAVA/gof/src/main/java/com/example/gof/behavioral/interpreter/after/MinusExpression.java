package com.example.gof.behavioral.interpreter.after;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MinusExpression implements PostfixExpression {
    private final PostfixExpression left, right;

    @Override
    public int interpret(Map<Character, Integer> context) {
        return left.interpret(context) - right.interpret(context);
    }
}
