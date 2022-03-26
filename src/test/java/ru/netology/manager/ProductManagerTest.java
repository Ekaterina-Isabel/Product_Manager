package ru.netology.manager;

import org.junit.jupiter.api.Test;
import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {
    ProductRepository repository = new ProductRepository();     //вызов конструктора, для создания объекта repository
    ProductManager manager = new ProductManager(repository);     //вызов конструктора, для создания manager

    Product book1 = new Book(1, "book1", 100, "author1");
    Product book2 = new Book(2, "book2", 110, "author2");
    Product smartphone1 = new Smartphone(3, "smartphone1", 120, "producer1");
    Product smartphone2 = new Smartphone(4, "smartphone2", 130, "producer2");

    @Test
    void shouldAddProducts() {        //добавь продукты
        repository.save(book1);
        repository.save(book2);
        repository.save(smartphone1);
        repository.save(smartphone2);

        Product[] expected = {book1, book2, smartphone1, smartphone2 };
        Product[] actual = manager.findAll();
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindById() {       //найди продукт по ID
        repository.save(book1);
        repository.save(book2);
        repository.save(smartphone1);
        repository.save(smartphone2);

        Product expecte = smartphone2;
        Product actual = manager.findById(4);
        assertEquals(expecte, actual);
    }

    @Test
    void shouldRemoveById() {     //удали продукт по id
        repository.save(book1);
        repository.save(book2);
        repository.save(smartphone1);
        repository.save(smartphone2);

        repository.removeById(4);

        Product[] expecte = {book1, book2, smartphone1};
        Product[] actual = manager.findAll();
        assertArrayEquals(expecte, actual);
    }

    @Test
    void shouldSearchBookByName() {     //найди книгу по имени
        repository.save(book1);
        repository.save(book2);
        repository.save(smartphone1);
        repository.save(smartphone2);

        Product[] expecte = {book1};
        Product[] actual = manager.searchByText("book1");
        assertArrayEquals(expecte, actual);
    }

    @Test
    void shouldSearchBookByAuthor() {     //найди книгу по автору
        repository.save(book1);
        repository.save(book2);
        repository.save(smartphone1);
        repository.save(smartphone2);

        Product[] expecte = {book1};
        Product[] actual = manager.searchByText("author1");
        assertArrayEquals(expecte, actual);
    }

    @Test
    void shouldSearchSmartphoneByName() {     //найди смартфон по имени
        repository.save(book1);
        repository.save(book2);
        repository.save(smartphone1);
        repository.save(smartphone2);

        Product[] expecte = {smartphone2};
        Product[] actual = manager.searchByText("smartphone2");
        assertArrayEquals(expecte, actual);
    }

    @Test
    void shouldSearchSmartphoneByProducer() {     //найди смартфон по производителю
        repository.save(book1);
        repository.save(book2);
        repository.save(smartphone1);
        repository.save(smartphone2);

        Product[] expecte = {smartphone2};
        Product[] actual = manager.searchByText("producer2");
        assertArrayEquals(expecte, actual);
    }

}