package library.repository;

import library.model.Loan;

public class ArrayLoanRepository implements LoanRepository {

    private Loan[] loans = new Loan[100];
    private int size = 0;

    @Override
    public void add(Loan loan) {
        if (size < loans.length) {
            loans[size] = loan;
            size++;
        } else {
            System.out.println("Журнал выдачи переполнен!");
        }
    }

    @Override
    public Loan findById(int id) {
        for (int i = 0; i < size; i++) {
            if (loans[i].getId() == id) {
                return loans[i];
            }
        }
        return null;
    }

    @Override
    public Loan[] getAll() {
        Loan[] result = new Loan[size];
        for (int i = 0; i < size; i++) {
            result[i] = loans[i];
        }
        return result;
    }
}