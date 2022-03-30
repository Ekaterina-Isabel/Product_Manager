package ru.netology.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldSearchProductByName() {     //найди продукт по названию
        Product product1 = new Product(1, "product1", 100);
        boolean expected = true;
        boolean actual = product1.matches("product1");

        assertEquals(expected, actual);
    }

    @Test
    void noShouldSearchProduct() {     // не должен найти продукт
        Product product1 = new Product(1, "product1", 100);
        boolean expected = false;
        boolean actual = product1.matches("product2");

        assertEquals(expected, actual);
    }
}