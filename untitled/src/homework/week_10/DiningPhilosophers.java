package homework.week_10;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    public static void main(String[] args) {
        int philosophersCount = 5;
        // Создаем массивы для потоков и общих ресурсов (вилок)
        Philosopher[] philosophers = new Philosopher[philosophersCount];
        Lock[] forks = new ReentrantLock[philosophersCount];

        // Заполняем массив вилок объектами ReentrantLock
        for (int i = 0; i < philosophersCount; i++) {
            forks[i] = new ReentrantLock();
        }

        // Настраиваем и запускаем каждого философа
        for (int i = 0; i < philosophersCount; i++) {
            Lock leftFork = forks[i];
            // Правая вилка — это следующая по индексу. Остаток от деления замыкает круг
            Lock rightFork = forks[(i + 1) % philosophersCount];

            // Последний философ берет вилки наоборот (сначала правую, потом левую)
            // Это спасает программу от бесконечной блокировки (deadlock)
            if (i == philosophersCount - 1) {
                philosophers[i] = new Philosopher(i + 1, rightFork, leftFork);
            } else {
                philosophers[i] = new Philosopher(i + 1, leftFork, rightFork);
            }

            // Запускаем поток
            new Thread(philosophers[i]).start();
        }
    }
}

// Класс философа, который реализует работу в отдельном потоке
class Philosopher implements Runnable {
    private final int id;
    private final Lock firstFork;
    private final Lock secondFork;

    public Philosopher(int id, Lock firstFork, Lock secondFork) {
        this.id = id;
        this.firstFork = firstFork;
        this.secondFork = secondFork;
    }

    @Override
    public void run() {
        try {
            // Каждый философ повторяет цикл 3 раза
            for (int i = 0; i < 3; i++) {

                // 1. Философ размышляет
                System.out.println("Философ " + id + " размышляет...");
                Thread.sleep(1000); // Простая пауза на 1 секунду

                // 2. Попытка взять вилки
                System.out.println("Философ " + id + " пытается взять вилки...");
                firstFork.lock(); // Берем первую вилку
                try {
                    secondFork.lock(); // Берем вторую вилку
                    try {

                        // 3. Философ ест
                        System.out.println("Философ " + id + " ест...");
                        Thread.sleep(1000); // Простая пауза на 1 секунду

                    } finally {
                        secondFork.unlock(); // Возвращаем вторую вилку
                    }
                } finally {
                    firstFork.unlock(); // Возвращаем первую вилку
                    System.out.println("Философ " + id + " положил вилки.");
                }
            }
        } catch (InterruptedException e) {
            // Обработка прерывания потока во время сна
            Thread.currentThread().interrupt();
        }
    }
}