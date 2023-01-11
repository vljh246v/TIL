package com.example.gof.structural.proxy.after;

public class DefaultGameService implements GameService {

    @Override
    public void startGame() {
        System.out.println("이 자리에 오신 여러분을 진심으로 환영합니다.");
    }

    public void endGame() {
        System.out.println("게임이 끝났습니다.");
    }
}
