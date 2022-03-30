package ru.netology.domain;

import org.junit.jupiter.api.Test;
import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void shouldSearchBookByName() {     //найди книгу по имени
        Book book1 = new Book(1, "book1", 100, "author1");
        boolean expected = true;
        boolean actual = book1.matches("book1");

        assertEquals(expected, actual);
    }

    @Test
    void shouldSearchBookByAuthor() {     //найди книгу по автору
        Book book1 = new Book(1, "book1", 100, "author1");
        boolean expected = true;
        boolean actual = book1.matches("author1");

        assertEquals(expected, actual);
    }

    @Test
    void noShouldSearchBook() {     // не должен найти книгу
        Book book1 = new Book(1, "book1", 100, "author1");
        boolean expected = false;
        boolean actual = book1.matches("author2");

        assertEquals(expected, actual);
    }
}