package homework.week_5;

/*
Задача: Создание класса Book
Описание
Вы — библиотекарь в небольшой библиотеке.
Ваша задача — создать программу, которая умеет хранить книги и показывать информацию о них.
Для этого нужно использовать классы, поля, методы и конструкторы.

Требования к классу Book
Класс должен иметь следующие поля:

title — название книги (строка)
author — автор книги (строка)
pages — количество страниц (целое число)
Класс должен иметь конструктор, который при создании объекта сразу задаёт все поля.

Класс должен иметь метод printInfo(), который выводит информацию о книге в удобном формате, например:

Название: Война и мир
Автор: Лев Толстой
Количество страниц: 1225

В main создайте несколько объектов класса Book и вызовите метод printInfo() для каждого.
Дополнительно
Если хотите сделать задачу интереснее, попробуйте добавить:

Проверку, что pages больше нуля.
Метод isLongBook(), который возвращает true, если книгa имеет больше 500 страниц.
Создать массив или список книг и вывести информацию о всех книгах в цикле.
Сделать несколько конструкторов:
Один задаёт все поля.
Второй — только название и автора (а страницы ставятся автоматически в 100).
 */

public class Book {

    private String title;
    private String author;
    private int pages;

    public Book(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        if (pages > 0) {
            this.pages = pages;
        } else {
            System.out.println("⚠️ Ошибка при создании «" + title + "»: страницы <= 0.");
            System.out.println("Установлено значение по умолчанию: 1 страница.\n");
            this.pages = 1;
        }
    }

    public Book(String title, String author) {
        this(title, author, 100);
    }

    public void printInfo() {
        System.out.println("Название: " + title);
        System.out.println("Автор: " + author);
        System.out.println("Количество страниц: " + pages);
    }

    public boolean isLongBook() {
        return pages > 500;
    }

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в нашу библиотеку!\n");

        Book book1 = new Book("Война и мир", "Лев Толстой", 1225);
        Book book2 = new Book("Мастер и Маргарита", "Михаил Булгаков", 480);
        Book book3 = new Book("Сборник стихов", "Александр Пушкин");
        Book book4 = new Book("Инструкция к чайнику", "Неизвестный", -50);

        Book[] library = {book1, book2, book3, book4};

        System.out.println("=== КАТАЛОГ КНИГ ===");

        for (Book currentBook : library) {
            currentBook.printInfo();

            if (currentBook.isLongBook()) {
                System.out.println("📌 Статус: Это очень толстая книга! Запаситесь терпением.");
            } else {
                System.out.println("📌 Статус: Книгу можно прочитать за пару вечеров.");
            }
            System.out.println("-------------------------");
        }
    }
}