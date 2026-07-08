package library.repository;

import library.model.Book;

public interface BookRepository {

    void add(Book book);

    Book findById(int id);

    Book[] getAll();
}