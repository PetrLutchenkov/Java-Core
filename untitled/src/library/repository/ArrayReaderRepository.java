package library.repository;

import library.model.Reader;

public class ArrayReaderRepository implements ReaderRepository {

    private Reader[] readers = new Reader[100];
    private int size = 0;

    @Override
    public void add(Reader reader) {
        if (size < readers.length) {
            readers[size] = reader;
            size++;
        } else {
            System.out.println("Ошибка: Хранилище читателей переполнено!");
        }
    }

    @Override
    public Reader findById(int id) {
        for (int i = 0; i < size; i++) {
            if (readers[i].getId() == id) {
                return readers[i];
            }
        }
        return null;
    }

    @Override
    public Reader[] getAll() {
        Reader[] result = new Reader[size];
        for (int i = 0; i < size; i++) {
            result[i] = readers[i];
        }
        return result;
    }
}