package me.whiteship.refactoring._06_mutable_data._19_separate_query_from_modifier;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CriminalTest {

    @Test
    void alertForMiscreant() {
        Criminal criminal = new Criminal();
        String found = criminal.alertForMiscreant(List.of(new Person("Keesun"), new Person("Don")));
        assertEquals("Don", found);

        found = criminal.alertForMiscreant(List.of(new Person("John"), new Person("Don")));
        assertEquals("John", found);
    }

}