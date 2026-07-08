package library.model;

public class Loan {
    private final int id;
    private final Book book;
    private final Reader reader;

    public Loan(int id, Book book, Reader reader) {
        this.id = id;
        this.book = book;
        this.reader = reader;
    }

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Reader getReader() {
        return reader;
    }

    @Override
    public String toString() {
        return "Запись [" + id + "]: Читатель " + reader.getName() +
                " взял книгу «" + book.getTitle() + "»";
    }
}