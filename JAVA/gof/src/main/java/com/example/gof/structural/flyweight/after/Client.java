package com.example.gof.structural.flyweight.after;

public class Client {

    public static void main(String[] args) {
        FontFactory fontFactory = new FontFactory();
        Character c1 = new Character('h', "read", fontFactory.getFont("nanum:12"));
        Character c2 = new Character('e', "black", fontFactory.getFont("nanum:12"));
        Character c3 = new Character('l', "green", fontFactory.getFont("nanum:12"));
        Character c4 = new Character('l', "white", fontFactory.getFont("nanum:12"));
        Character c5 = new Character('o', "red", fontFactory.getFont("nanum:12"));
    }
}
