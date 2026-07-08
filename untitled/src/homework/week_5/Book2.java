package homework.week_5;

/*
1) Добавьте в класс Book поле year — год издания.
Сделайте для него геттер и сеттер.
В сеттере добавьте проверку: год издания не может быть меньше 1500 и больше текущего года.
Создайте объект книги и попробуйте задать разные значения года через сеттер, выводя результат через геттер.
Попробуйте вывести всю информацию о книге с помощью printInfo().

2) В классе Book создайте ещё один конструктор, который задаёт только title (остальные поля по умолчанию).
Добавьте метод updatePages() и перегрузите его:
updatePages(int pages) — задаёт количество страниц.
updatePages(int pages, boolean increase) — если increase == true, прибавляет страницы к текущим.
Создайте несколько книг и попробуйте разные варианты конструкторов и методов, выводя результат через printInfo().

3) В классе Book добавьте статическое поле libraryName — название библиотеки.
Сделайте статический метод printLibraryName(), который выводит название библиотеки.
Создайте несколько книг и убедитесь, что все видят одну и ту же библиотеку.
Попробуйте изменить libraryName через класс и через объект — что произойдёт?
 */

public class Book2 {
    private String title;
    private String author;
    private int pages;
    private int year;
    public static String libraryName = "РНБ";

    public Book2(String title, String author, int pages, int year) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
    }
    public Book2(String title, String author, int pages) {
        this(title,author, pages, 1);
    }
    public Book2(String title) {
        this(title, "Неизвестный автор", 1, 1);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(int pages) {
        if (pages > 0) {
            this.pages = pages;
        } else {
            System.out.println("Количество страниц должно быть больше 0");
        }
    }

    public void setYear(int year) {
        if (year >= 1500 && year < 2026) {
            this.year = year;
        } else {
            System.out.println("Год написания книги должен быть больше 1500 и меньше 2026");
        }
    }
    public void updatePages(int pages) {
        if (pages > 0) {
            this.pages = pages;
            System.out.println("🔄 Страницы для «" + title + "» успешно обновлены: " + this.pages);
        } else {
            System.out.println("⚠️ Ошибка: количество страниц должно быть больше нуля!");
        }
    }

    // Вариант 2: Прибавляет или отнимает страницы от ТЕКУЩЕГО количества
    public void updatePages(int pages, boolean increase) {
        if (increase) {
            // Если true -> прибавляем
            this.pages += pages;
            System.out.println("➕ К книге «" + title + "» добавлено " + pages + " стр. Теперь их: " + this.pages);
        } else {
            // Если false -> логично предположить, что мы отнимаем (например, вырезали главу)
            if (this.pages - pages > 0) {
                this.pages -= pages;
                System.out.println("➖ Из книги «" + title + "» удалено " + pages + " стр. Теперь их: " + this.pages);
            } else {
                System.out.println("⚠️ Ошибка: нельзя удалить больше страниц, чем есть в книге!");
            }
        }
    }

    public void printInfo() {
        System.out.println("Книга: «" + title + "» | Автор: " + author + " | Страниц: " + pages + " | Год: " + year);
    }
    public static void printLibraryName() {
        System.out.println("Библиотека " + Book2.libraryName);
    }
}

class Main2 {
    static void main(String[] args) {
        System.out.println("=== Моя Библиотека_2 ===\n");
        Book2.printLibraryName();
        Book2 book1 = new Book2("1984", "Джордж Оруэлл", 350);
        Book2 book2 = new Book2("Властелин Колец", "Дж. Р. Р. Толкин", 1100);
        Book2 book3 = new Book2("Мастер и Маргарита", "Михаил Булгаков", 480);
        book1.printInfo();
        book2.printInfo();
        book3.printInfo();
        Book2 book4 = new Book2("Война и мир", "Лев Толстой", 1225, 1600);
        Book2.libraryName = "Российская Национальная Библиотека";

        // Получаем данные через геттеры
        System.out.println("Год книги: " + book4.getYear());

        // Изменяем количество страниц через сеттер
        book4.setYear(2000);
        book4.printInfo();

        // Попытка задать отрицательное значение
        book4.setYear(1300); // сработает проверка

        System.out.println("\n=== ТЕСТИРОВАНИЕ ПЕРЕГРУЖЕННЫХ МЕТОДОВ ===\n");

        // Тестируем Вариант 1: Жесткая установка страниц
        book2.updatePages(150); // Вызовется метод с 1 параметром

        // Тестируем Вариант 2: Добавление страниц (true)
        book1.updatePages(50, true); // Вызовется метод с 2 параметрами

        // Тестируем Вариант 2: Вычитание страниц (false)
        book1.updatePages(20, false);

        // Проверка защиты (попытка удалить 1000 страниц из книги, где их меньше 1000)
        book1.updatePages(1000, false);

        Book2.printLibraryName();
    }

}