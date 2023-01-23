## Правильно настроен Maven-проект, тесты проходят
  
  Репозиторий должен быть папкой вашего мавен-проекта. Обратите внимание, что репозиторием не должна быть папка в которой лежит папка мавен-проекта, он сам должен быть папкой проекта. В нём должны быть соответствующие файлы и папки - `pom.xml`, `src` и др.
  
  Не забудьте создать .gitignore-файл в корне проекта и добавить туда в игнорирование автогенерируемую папку `target`.
  
  Общая схема вашего `pom.xml`-файла:
  
  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>ru.netology</groupId>
    <artifactId>НАЗВАНИЕ-ВАШЕГО-ПРОЕКТА-БЕЗ-ПРОБЕЛОВ</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>


    <dependencies>
        <dependency>
            ...
        </dependency>
        ...
    </dependencies>


    <build>
        <plugins>
            <plugin>
              ...
            </plugin>

            <plugin>
              ...
              <executions>
                <execution>
                  ...
                </execution>
                ...
              </executions>
            </plugin>
            ...
        </plugins>
    </build>

</project>
  ```
  
  #### JUnit
  Обратите внимание что у артефакта нет `-api` на конце. Если у вас автоматически добавилась зависимость вида `<artifactId>junit-jupiter-api</artifactId>`, то лучше поменять артефакт на тот что ниже, иначе будут сюрпризы в работе.

  ```xml
          <dependency>
              <groupId>org.junit.jupiter</groupId>
              <artifactId>junit-jupiter</artifactId>
              <version>5.7.0</version>
              <scope>test</scope>
          </dependency>
  ```

  #### Surefire
  Без этого плагина тесты могут мавеном не запускаться, хоть в идее через кнопки они и будут проходить. Чтобы лишний раз убедиться, что всё работает, нажмите `Ctrl+Ctrl` и затем `mvn clean test`.
  
  ```xml
              <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-surefire-plugin</artifactId>
                  <version>2.22.2</version>
                  <configuration>
                      <failIfNoTests>true</failIfNoTests>
                  </configuration>
              </plugin>
  ```
  
  ---
  
## Отформатирован код
  
  Кроме правил, нарушение которых приводит к ошибкам компиляции, есть ещё и [правила форматирования кода](https://google.github.io/styleguide/javaguide.html), соблюдение которых обязательно при напиании программ.
  
  С большинством проблем может справиться автоформатирование в идее. Для этого выберите `Code -> Reformat code` в меню или используйте горячие сочетания клавиш (в меню будет показано актуальное сочетание для вашей операционной системы). Так, идея поправит неправильные отступы, пробелы и некоторые другие ошибки. Следите, чтобы у `if-else`, `for`, `while` всегда были `{}`.
  
  Проблемы с именованием сущностей нужно решать самим. Так, все ячейки кроме `final`-констант и методы должны писаться с маленькой буквы [камелкейсом](https://ru.wikipedia.org/wiki/CamelCase) с **маленькой** буквы, а классы и интерфейсы - камелкейсом с **большой** буквы.
  
  Мы вам настоятельно советуем всегда держать код в отформатированном виде во время разработки, со временем глаз привыкнет и вы почувствуете насколько это облегчает поиск ошибок в коде и его анализ. В любом случае, перед отправкой кода на проверку его обязательно нужно отформатировать, иначе он может быть отправлен на доработку без более глубокой проверки на этой итерации.


## Настроен Github CI с verify-сборкой Maven и JaCoCo в режиме генерации отчётов с покрытием на 100% по бранчам методов с логикой
  
  #### CI
  После связывания локального репозитория с удалённым и первого пуша в заготовки проекта, время настроить CI на основе Github Actions. Шаблон вашего maven.yml должен выглядеть вот так, убедитесь что всё совпадает с вашим шаблоном (например, что вы указали фазу `verify`, а не `package`):
  ```yml
  name: Java CI with Maven

  on: [push, pull_request]

  jobs:
    build:

      runs-on: ubuntu-latest

      steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          java-version: '11'
          distribution: 'adopt'
      - name: Build with Maven
        run: mvn -B -e verify
  ```
  
  #### JaCoCo

  ```xml
              <plugin>
                  <groupId>org.jacoco</groupId>
                  <artifactId>jacoco-maven-plugin</artifactId>
                  <version>0.8.5</version>
                  ...
  ```

  Инициализация:
  ```xml
                      <execution>
                          <id>prepare-agent</id>
                          <goals>
                              <goal>prepare-agent</goal>
                          </goals>
                      </execution>
  ```

  В режиме генерации отчётов:
  ```xml
                      <execution>
                          <id>report</id>
                          <phase>verify</phase>
                          <goals>
                              <goal>report</goal>
                          </goals>
                      </execution>
  ```

  В режиме проверки и обрушения сборки по уровню покрытия:
  ```xml
                      <execution>
                          <id>check</id>
                          <goals>
                              <goal>check</goal>
                          </goals>
                          <configuration>
                              <rules>
                                  <rule>
                                      <limits>
                                          <limit>
                                              <counter>LINE</counter>
                                              <value>COVEREDRATIO</value>
                                              <minimum>100%</minimum>
                                          </limit>
                                      </limits>
                                  </rule>
                              </rules>
                          </configuration>
                      </execution>
  ```


## Задание 1. Менеджер Товаров

Вам необходимо реализовать менеджер товаров, который умеет:

1. Добавлять товары в репозиторий
1. Искать товары

Что нужно сделать:
1. Разработайте базовый класс `Product`, содержащий `id`, название, стоимость
1. Разработать два унаследованных от `Product` класса: `Book` (с текстовыми полями название и автор) и `Smartphone` (с текстовыми полями название и производитель); общие поля вынесите в родителя.
1. Разработайте репозиторий, позволяющий сохранять `Product`'ы, получать все сохранённые `Product`'ы и удалять по `id`. Для этого репозиторий будет хранить у себя поле с типом `Product[]` (массив товаров).
1. Разработайте менеджера, который умеет добавлять `Product`'ы в репозиторий и осуществлять поиск по ним. Для этого вам нужно создать класс, конструктор которого будет принимать параметром репозиторий, а также с методом `publiс void add(Product product)` и методом поиска (см. ниже).

#### Как осуществлять поиск

У менеджера должен быть метод `searchBy(String text)`, который возвращает массив найденных товаров 

```java
public class ProductManager {
  // добавьте необходимые поля, конструкторы и методы

  public Product[] searchBy(String text) {
    Product[] result = new Product[0]; // тут будем хранить подошедшие запросу продукты
    for (Product product: repository.findAll()) {
      if (matches(product, text)) {
        // "добавляем в конец" массива result продукт product
      }
    }
    return result;
  }

  // метод определения соответствия товара product запросу search
  public boolean matches(Product product, String search) {
    if (product.getName().contains(search)) {
      return true;
    } else {
      return false;
    }
    // или в одну строку:
    // return product.getName().contains(search);
  }
}
```

Менеджер при переборе всех продуктов, хранящихся в репозитории, должен для каждого продукта вызывать определённый в классе менеджера же метод `matches`, который проверяет, соответствует ли продукт поисковому запросу.

При проверке на соответствие запросу товару мы проверяем вхождение запроса в текст названия товара.

Напишите тесты на менеджер и репозиторий, добившись 100% покрытия по бранчам методов с логикой.

Итого: отправьте на проверку ссылку на гитхаб-репозиторий с вашим проектом. 

## Задание 2*. Менеджер Товаров (Rich Model)

Достаточно часто объекты, моделирующие предметную область, называют моделями. Достаточно часто модели делают "глупыми", т.е. не содержащими никакой логики.

Но есть и другой подход, который позволяет делать "умные" или "богатые" модели (Rich Model), которые могут уже содержать определённую логику.

Что нужно сделать:
1. Создайте в том же что и первое задание репозитории новую ветку `rich` на базе ветки первого задания
1. Реализуйте в классе `Product` метод `public boolean matches(String search)`, который определяет, подходит ли продукт поисковому запросу исходя из названия
1. Переопределите этот метод в дочерних классах, чтобы они сначала вызывали родительский метод и только если родительский метод вернул `false`, тогда проводили доп.проверки (`Book` - по автору, `Smartphone` - по производителю).
1. Замените в менеджере вызов метода `matches` из класса самого менеджера на вызов метода `matches` у самих продуктов: `if (product.matches(search)) {`
1. Теперь вам нужны unit-тесты на методы ваших умных моделей (напишите их)
1. Удостоверьтесь, что ранее написанные тесты на менеджера (из решения задачи 1) проходят
1. Добейтесь 100% покрытия по бранчам методов с логикой 

Заготовка для метода `matches` у класса `Book`:
```java
public class Book extends Product {
  // ваши поля, конструкторы, методы
  public boolean matches(String search) {
    if (super.matches(search)) { // вызов метода matches в версии описанной в Product
      return true;
    }
    if ...;
  }
}
```

**Итого:** отправьте на проверку ссылку на пулл-реквест ветки `rich` с вашим проектом. 
