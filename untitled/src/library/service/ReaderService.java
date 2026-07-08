package library.service;

import library.model.Reader;
import library.repository.ReaderRepository;

public class ReaderService {

    private final ReaderRepository readerRepository;

    private int nextReaderId = 1;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public void addReader(String name, String phone) {
        if (name == null || name.isBlank()) {
            System.out.println("Имя читателя не может быть пустым!");
            return;
        }
        if (phone == null || phone.isBlank()) {
            System.out.println("Номер телефона не может быть пустым!");
            return;
        }

        Reader newReader = new Reader(nextReaderId, name, phone);
        nextReaderId++;

        readerRepository.add(newReader);
        System.out.println("Читатель '" + name + "' зарегистрирован в библиотеке.");
    }

    public void printAllReaders() {
        Reader[] readers = readerRepository.getAll();

        if (readers.length == 0) {
            System.out.println("База читателей пока пуста.");
            return;
        }

        System.out.println("\n=== СПИСОК ЧИТАТЕЛЕЙ ===");
        for (int i = 0; i < readers.length; i++) {
            System.out.println(readers[i].toString());
        }
        System.out.println("===========================\n");
    }
}