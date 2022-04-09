package com.example.gof.structural.composite.before;

public class Client {

    public static void main(String[] args) {
        Item item1 = new Item("item1", 500);
        Item item2 = new Item("item2", 100);

        Bag bag = new Bag();
        bag.add(item1);
        bag.add(item2);

        Client client = new Client();
        client.printPrice(item1);
        client.printPrice(bag);
    }

    private void printPrice(Item item) {
        System.out.println(item.getPrice());
    }

    private void printPrice(Bag bag) {
        int sum = bag.getItems().stream().mapToInt(Item::getPrice).sum();
        System.out.println(sum);
    }
}
