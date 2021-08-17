package com.jaehyun.kotlin.study.study.ch04;

import org.jetbrains.annotations.NotNull;

public class Button implements View {

    @NotNull
    @Override
    public State getCurrentState() {
        Button button = new Button();

        return new ButtonState1();
    }

    @Override
    public void restoreState(@NotNull State state) { /*...*/ }

    public class ButtonState1 implements State { /*...*/ }

    public static class ButtonState2 implements State { /*...*/ }
}

