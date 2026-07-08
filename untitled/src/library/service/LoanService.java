package library.service;

import library.model.Book;
import library.model.Loan;
import library.model.Reader;
import library.repository.BookRepository;
import library.repository.LoanRepository;
import library.repository.ReaderRepository;

public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    private int nextLoanId = 1;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public void borrowBook(int bookId, int readerId) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            System.out.println("Книга с ID " + bookId + " не найдена.");
            return;
        }

        Reader reader = readerRepository.findById(readerId);
        if (reader == null) {
            System.out.println("Читатель с ID " + readerId + " не найден.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Книга «" + book.getTitle() + "» уже на руках!");
            return;
        }

        book.setAvailable(false);

        Loan newLoan = new Loan(nextLoanId, book, reader);
        nextLoanId++;

        loanRepository.add(newLoan);
        System.out.println(reader.getName() + " взял(а) книгу «" + book.getTitle() + "».");
    }

    public void returnBook(int bookId) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            System.out.println("Книга с ID " + bookId + " не найдена.");
            return;
        }

        if (book.isAvailable()) {
            System.out.println("Книга «" + book.getTitle() + "» и так находится в библиотеке!");
            return;
        }

        book.setAvailable(true);
        System.out.println("Книга «" + book.getTitle() + "» успешно возвращена на полку.");
    }

    public void printAllLoans() {
        Loan[] loans = loanRepository.getAll();
        if (loans.length == 0) {
            System.out.println("Журнал выдачи пока пуст.");
            return;
        }

        System.out.println("\n=== ЖУРНАЛ ВЫДАЧИ ===");
        for (int i = 0; i < loans.length; i++) {
            System.out.println(loans[i].toString());
        }
        System.out.println("========================\n");
    }
}