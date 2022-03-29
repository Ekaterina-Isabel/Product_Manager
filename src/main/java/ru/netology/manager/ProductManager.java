package ru.netology.manager;

import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.repository.ProductRepository;

public class ProductManager {
    private ProductRepository repository;      //repository - там менеджер хранит информацию

    public ProductManager(ProductRepository repository) {       //конструктор, принимает параметром репозиторий
        this.repository = repository;
    }

    public void add(Product product) {     //команда менеджеру добавить продукт - репозиторий запомни
        repository.save(product);
    }

    public Product[] findAll() {        //вывод всех продуктов
        return repository.findAll();
    }

    public Product findById(int id) {       //возвращает фильм по id (либо null, если такого объекта нет)
        return repository.findById(id);
    }

    public void removeById(int id) {        //удаляет объект по id
        repository.removeById(id);
    }

    public Product[] searchByText(String text) {
        Product[] result = new Product[0];      //result - для хранения подошедших запросу продуктов
        for (Product product : repository.findAll()) {
            if (product.matches(text)) {       //"добавляем в конец" массива result продукт product
                Product[] tmp = new Product[result.length + 1];     //tmp - временный массив для сохранения результата
                System.arraycopy(result, 0, tmp, 0, result.length);     //копируем результат во временный массив
                tmp[tmp.length - 1] = product;
                result = tmp;
            }
        }
        return result;
    }
}
