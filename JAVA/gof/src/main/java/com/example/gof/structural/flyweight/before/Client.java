package com.example.gof.structural.flyweight.before;

public class Client {

    public static void main(String[] args) {
        Character c1 = new Character('h', "read", "Nanum", 12);
        Character c2 = new Character('e', "black", "Nanum", 12);
        Character c3 = new Character('l', "green", "Nanum", 12);
        Character c4 = new Character('l', "white", "Nanum", 12);
        Character c5 = new Character('o', "red", "Nanum", 12);
    }
}
