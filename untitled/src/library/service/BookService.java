package library.service;

import library.model.Book;
import library.repository.BookRepository;

public class BookService {
    private final BookRepository bookRepository;

    private int nextBookId = 1;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String title, String author) {
        if (title == null || title.isBlank()) {
            System.out.println("Название книги не может быть пустым!");
            return;
        }
        if (author == null || author.isBlank()) {
            System.out.println("У книги должен быть автор!");
            return;
        }

        Book newBook = new Book(nextBookId, title, author);
        nextBookId++;

        bookRepository.add(newBook);
        System.out.println("Книга '" + title + "' добавлена в библиотеку.");
    }

    public void printAllBooks() {
        Book[] books = bookRepository.getAll();

        if (books.length == 0) {
            System.out.println("Библиотека пока пуста.\n");
            return;
        }

        System.out.println("\n=== СПИСОК КНИГ ===");
        for (Book book : books) {
            System.out.println(book.toString());
        }
        System.out.println("=======================\n");
    }
}