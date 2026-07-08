package library.repository;

import library.model.Loan;

public interface LoanRepository {
    void add(Loan loan);

    Loan findById(int id);

    Loan[] getAll();
}