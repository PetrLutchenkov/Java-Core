package library;

import library.repository.*;
import library.service.BookService;
import library.service.ReaderService;
import library.service.LoanService;

public class Main {
    public static void main(String[] args) {

        // 1. СБОРКА АРХИТЕКТУРЫ
        BookRepository bookRepo = new ArrayBookRepository();
        ReaderRepository readerRepo = new ArrayReaderRepository();
        LoanRepository loanRepo = new ArrayLoanRepository();

        BookService bookService = new BookService(bookRepo);
        ReaderService readerService = new ReaderService(readerRepo);
        LoanService loanService = new LoanService(loanRepo, bookRepo, readerRepo);

        // 2. ЗАВОЗИМ ДАННЫЕ
        bookService.addBook("1984", "Джордж Оруэлл"); // ID 1
        bookService.addBook("Властелин Колец", "Дж.Р.Р. Толкин"); // ID 2

        readerService.addReader("Иван Иванов", "+7 (999) 123-45-67"); // ID 1
        readerService.addReader("Петр Петров", "+7 (900) 000-00-00"); // ID 2
        System.out.println();

        // 3. РАЗЫГРЫВАЕМ СЦЕНКУ
        System.out.println("--- ДЕНЬ 1 ---");
        // Иван (ID 1) берет "1984" (ID 1)
        loanService.borrowBook(1, 1);

        // Петр (ID 2) приходит за "1984" (ID 1), но она занята!
        loanService.borrowBook(1, 2);
        System.out.println();

        System.out.println("--- ДЕНЬ 2 ---");
        // Иван прочитал и возвращает "1984" (ID 1)
        loanService.returnBook(1);

        // Теперь Петр (ID 2) снова пытается взять "1984" (ID 1) - и у него получается!
        loanService.borrowBook(1, 2);
        System.out.println();

        // 4. ПРОВЕРЯЕМ ИТОГИ
        System.out.println("--- ИТОГОВОЕ СОСТОЯНИЕ ---");
        loanService.printAllLoans();
        bookService.printAllBooks();
    }
}