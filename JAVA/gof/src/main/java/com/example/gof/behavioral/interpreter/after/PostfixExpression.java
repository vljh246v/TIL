package com.example.gof.behavioral.interpreter.after;

import java.util.Map;

public interface PostfixExpression {
    int interpret(Map<Character, Integer> context);
}
