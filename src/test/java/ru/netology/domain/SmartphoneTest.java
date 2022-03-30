package ru.netology.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartphoneTest {

    @Test
    void shouldSearchSmartphoneByName() {     //найди смартфон по имени
        Smartphone smartphone1 = new Smartphone(1, "smartphone1", 100, "producer1");
        boolean expected = true;
        boolean actual = smartphone1.matches("smartphone1");

        assertEquals(expected, actual);
    }

    @Test
    void shouldSearchSmartphoneByProducer() {     //найди смартфон по производителю
        Smartphone smartphone1 = new Smartphone(1, "smartphone1", 100, "producer1");
        boolean expected = true;
        boolean actual = smartphone1.matches("producer1");

        assertEquals(expected, actual);
    }

    @Test
    void noShouldSearchSmartphone() {     // не должен найти смартфон
        Smartphone smartphone1 = new Smartphone(1, "smartphone1", 100, "producer1");
        boolean expected = false;
        boolean actual = smartphone1.matches("author2");

        assertEquals(expected, actual);
    }
}