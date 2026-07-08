package homework.week_8;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

// --- Перечисления ---
enum Genre { FICTION, SCIENCE, HISTORY, BIOGRAPHY, FANTASY, MYSTERY, ROMANCE, TECHNOLOGY }
enum BookStatus { AVAILABLE, BORROWED, RESERVED, UNDER_MAINTENANCE }
enum ReaderType { STUDENT, TEACHER, STAFF, EXTERNAL }

// --- Классы сущностей ---
class Book implements Comparable<Book> {
    private String isbn;
    private String title;
    private String author;
    private Genre genre;
    private int publicationYear;
    private BookStatus status;
    private LocalDate dueDate;
    private String borrowedBy;

    public Book(String isbn, String title, String author, Genre genre, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.status = BookStatus.AVAILABLE;
    }

    // Геттеры
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public Genre getGenre() { return genre; }
    public int getPublicationYear() { return publicationYear; }
    public BookStatus getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }
    public String getBorrowedBy() { return borrowedBy; }

    // Сеттеры
    public void setStatus(BookStatus status) { this.status = status; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setBorrowedBy(String borrowedBy) { this.borrowedBy = borrowedBy; }

    @Override
    public int compareTo(Book other) {
        int authorCompare = this.author.compareTo(other.author);
        return (authorCompare != 0) ? authorCompare : this.title.compareTo(other.title);
    }

    @Override
    public String toString() {
        return String.format("'%s' - %s (%d) [%s] - Статус: %s", title, author, publicationYear, genre, status);
    }
}

class Reader {
    private String readerId;
    private String name;
    private ReaderType type;
    private String email;
    private LocalDate registrationDate;

    public Reader(String readerId, String name, ReaderType type, String email) {
        this.readerId = readerId;
        this.name = name;
        this.type = type;
        this.email = email;
        this.registrationDate = LocalDate.now();
    }

    public String getReaderId() { return readerId; }
    public String getName() { return name; }
    public ReaderType getType() { return type; }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, type);
    }
}

// --- Основная система ---
public class LibraryManagementSystem {

    // 1. HashMap: Быстрый доступ O(1)
    private final HashMap<String, Book> booksByIsbn = new HashMap<>();
    private final HashMap<String, Reader> readersById = new HashMap<>();
    private final HashMap<String, List<Book>> borrowedBooks = new HashMap<>();

    // 2. TreeMap: Автоматическая сортировка O(log n)
    private final TreeMap<String, Book> booksByTitle = new TreeMap<>();
    private final TreeMap<ReaderType, List<Reader>> readersByType = new TreeMap<>();

    // 3. LinkedHashMap: Сохранение хронологии добавления
    private final LinkedHashMap<String, Book> recentlyAddedBooks = new LinkedHashMap<>();

    // 4. EnumMap: Сверхбыстрая работа с ключами-перечислениями (массив под капотом)
    private final EnumMap<Genre, List<Book>> booksByGenre = new EnumMap<>(Genre.class);

    // --- Управление книгами ---

    public boolean addBook(Book book) {
        if (booksByIsbn.containsKey(book.getIsbn())) return false;

        booksByIsbn.put(book.getIsbn(), book);
        booksByTitle.put(book.getTitle(), book);
        recentlyAddedBooks.put(book.getIsbn(), book);

        // МАГИЯ: computeIfAbsent создаст список, если жанра еще нет в мапе, и сразу добавит туда книгу
        booksByGenre.computeIfAbsent(book.getGenre(), k -> new ArrayList<>()).add(book);
        return true;
    }

    public boolean removeBook(String isbn) {
        Book book = booksByIsbn.remove(isbn);
        if (book == null) return false;

        booksByTitle.remove(book.getTitle());
        recentlyAddedBooks.remove(isbn);

        // Безопасное удаление из списка жанров
        booksByGenre.computeIfPresent(book.getGenre(), (k, list) -> {
            list.remove(book);
            return list.isEmpty() ? null : list; // Если список пуст, удаляем ключ из мапы
        });
        return true;
    }

    // --- Управление читателями ---

    public boolean registerReader(Reader reader) {
        if (readersById.putIfAbsent(reader.getReaderId(), reader) != null) return false;

        readersByType.computeIfAbsent(reader.getType(), k -> new ArrayList<>()).add(reader);
        return true;
    }

    public boolean borrowBook(String isbn, String readerId, int days) {
        Book book = booksByIsbn.get(isbn);
        Reader reader = readersById.get(readerId);

        if (book == null || reader == null || book.getStatus() != BookStatus.AVAILABLE) {
            return false;
        }

        book.setStatus(BookStatus.BORROWED);
        book.setDueDate(LocalDate.now().plusDays(days));
        book.setBorrowedBy(readerId);

        borrowedBooks.computeIfAbsent(readerId, k -> new ArrayList<>()).add(book);
        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = booksByIsbn.get(isbn);
        if (book == null || book.getStatus() != BookStatus.BORROWED) return false;

        String readerId = book.getBorrowedBy();
        book.setStatus(BookStatus.AVAILABLE);
        book.setDueDate(null);
        book.setBorrowedBy(null);

        borrowedBooks.computeIfPresent(readerId, (k, books) -> {
            books.removeIf(b -> b.getIsbn().equals(isbn));
            return books.isEmpty() ? null : books;
        });
        return true;
    }

    // --- Поиск и получение данных ---

    public Book findBookByIsbn(String isbn) {
        // Мгновенный поиск O(1)
        return booksByIsbn.get(isbn);
    }

    public List<Book> getBooksByGenre(Genre genre) {
        // getOrDefault защищает от NPE, если жанр пуст
        return booksByGenre.getOrDefault(genre, Collections.emptyList());
    }

    public List<Book> getRecentlyAddedBooks(int count) {
        // LinkedHashMap хранит порядок. Берем значения, разворачиваем и отрезаем нужное количество
        List<Book> allRecent = new ArrayList<>(recentlyAddedBooks.values());
        Collections.reverse(allRecent);
        return allRecent.stream().limit(count).collect(Collectors.toList());
    }

    // --- Сортировка и упорядочивание ---

    public List<Book> getBooksSortedByTitle() {
        // TreeMap уже отсортирован по ключам (названиям), просто забираем значения
        return new ArrayList<>(booksByTitle.values());
    }

    public List<Book> getBooksSortedByYear() {
        // Временный TreeMap. Важно: если года совпадают, сортируем по ISBN, чтобы книги не затерли друг друга!
        TreeMap<Book, String> sortedByYear = new TreeMap<>(
                Comparator.comparingInt(Book::getPublicationYear)
                        .thenComparing(Book::getIsbn)
        );
        booksByIsbn.values().forEach(b -> sortedByYear.put(b, b.getIsbn()));
        return new ArrayList<>(sortedByYear.keySet());
    }

    // --- Статистика и анализ ---

    public Map<String, Object> getLibraryStatistics() {
        Map<String, Object> stats = new LinkedHashMap<>(); // Сохраняем порядок вывода статистики
        stats.put("Всего книг", booksByIsbn.size());
        stats.put("Всего читателей", readersById.size());
        stats.put("Книг на руках", borrowedBooks.values().stream().mapToInt(List::size).sum());
        return stats;
    }

    public Map<Genre, Integer> getGenreStatistics() {
        Map<Genre, Integer> stats = new EnumMap<>(Genre.class);
        booksByGenre.forEach((genre, list) -> stats.put(genre, list.size()));
        return stats;
    }

    // --- Утилитные методы Map ---

    public Map<String, Book> getAllAvailableBooks() {
        return booksByIsbn.entrySet().stream()
                .filter(entry -> entry.getValue().getStatus() == BookStatus.AVAILABLE)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // --- MAIN ---
    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();

        // 1. Добавляем книги
        library.addBook(new Book("ISBN-1", "Война и мир", "Лев Толстой", Genre.FICTION, 1869));
        library.addBook(new Book("ISBN-2", "Краткая история времени", "Стивен Хокинг", Genre.SCIENCE, 1988));
        library.addBook(new Book("ISBN-3", "Мастер и Маргарита", "Михаил Булгаков", Genre.FICTION, 1967));
        library.addBook(new Book("ISBN-4", "Java: Полное руководство", "Герберт Шилдт", Genre.TECHNOLOGY, 2022));

        // 2. Регистрируем читателей
        library.registerReader(new Reader("R1", "Иван Иванов", ReaderType.STUDENT, "ivan@test.com"));
        library.registerReader(new Reader("R2", "Мария Петрова", ReaderType.TEACHER, "maria@test.com"));

        // 3. Выдача книг
        library.borrowBook("ISBN-1", "R1", 14);
        library.borrowBook("ISBN-4", "R2", 30);

        // --- Тестирование результатов ---
        System.out.println("=== Книги жанра FICTION ===");
        library.getBooksByGenre(Genre.FICTION).forEach(System.out::println);

        System.out.println("\n=== Последние добавленные книги (2 шт) ===");
        library.getRecentlyAddedBooks(2).forEach(System.out::println);

        System.out.println("\n=== Книги по году издания ===");
        library.getBooksSortedByYear().forEach(System.out::println);

        System.out.println("\n=== Доступные сейчас книги ===");
        library.getAllAvailableBooks().values().forEach(System.out::println);

        System.out.println("\n=== Статистика библиотеки ===");
        library.getLibraryStatistics().forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\n=== Статистика по жанрам ===");
        library.getGenreStatistics().forEach((k, v) -> System.out.println(k + ": " + v + " шт."));
    }
}