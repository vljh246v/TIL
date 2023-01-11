package com.example.gof.structural.composite.after;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class Bag implements Component {

    private final List<Component> components = new ArrayList<>();

    public Bag(List<Component> components) {
        this.components.addAll(components);
    }

    public void add(Component component) {
        this.components.add(component);
    }

    public void addAll(List<Component> components) {
        this.components.addAll(components);
    }

    public List<Component> getComponents() {
        return components;
    }

    @Override
    public int getPrice() {
        return components.stream().mapToInt(Component::getPrice).sum();
    }
}
