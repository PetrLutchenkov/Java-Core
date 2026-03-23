package library.repository;

import library.model.Book;

public class ArrayBookRepository implements BookRepository {

    private final Book[] books = new Book[100];

    private int size = 0;

    @Override
    public void add(Book book) {
        if (size < books.length) {
            books[size] = book;
            size++;
        } else {
            System.out.println("Хранилище книг переполнено!");
        }
    }

    @Override
    public Book findById(int id) {
        for (int i = 0; i < size; i++) {
            if (books[i].getId() == id) {
                return books[i];
            }
        }
        return null;
    }

    @Override
    public Book[] getAll() {
        Book[] result = new Book[size];
        for (int i = 0; i < size; i++) {
            result[i] = books[i];
        }
        return result;
    }
}