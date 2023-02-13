package me.whiteship.refactoring._11_primitive_obsession._32_replace_conditional_with_polymorphism.variation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VoyageRatingTest {

    @Test
    void westIndies() {
        VoyageRating voyageRating = new VoyageRating(new Voyage("west-inides", 10),
                List.of(new VoyageHistory("east-indies", 5),
                        new VoyageHistory("west-indies", 15),
                        new VoyageHistory("china", -2),
                        new VoyageHistory("west-africa", 7)));
        assertEquals('B', voyageRating.value());
    }

    @Test
    void china() {
        VoyageRating voyageRating = new VoyageRating(new Voyage("china", 10),
                List.of(new VoyageHistory("east-indies", 5),
                        new VoyageHistory("west-indies", 15),
                        new VoyageHistory("china", -2),
                        new VoyageHistory("west-africa", 7)));
        assertEquals('A', voyageRating.value());
    }

}