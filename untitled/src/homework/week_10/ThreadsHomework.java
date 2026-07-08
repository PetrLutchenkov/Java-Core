package homework.week_10;

// Способ 1: Наследование от класса Thread
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Поток " + Thread.currentThread().getName() + " начал работу");
    }
}

// Способ 2 (Дополнительный): Реализация интерфейса Runnable
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Поток " + Thread.currentThread().getName() + " начал работу (через Runnable)");
    }
}

// Главный класс программы
public class ThreadsHomework {
    public static void main(String[] args) {
        System.out.println("--- ЗАПУСК ПОТОКОВ ЧЕРЕЗ НАСЛЕДОВАНИЕ ---");

        // Создаем 3 потока первым способом
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();
        MyThread thread3 = new MyThread();

        // Дергаем JNI-метод start(), чтобы ОС выделила память и запустила их
        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println("\n--- ЗАПУСК ПОТОКОВ ЧЕРЕЗ ИНТЕРФЕЙС ---");

        // Создаем 1 инструкцию (задачу)
        MyRunnable task = new MyRunnable();

        // Нанимаем 2 новых рабочих (потока) и даем им одинаковую инструкцию
        Thread thread4 = new Thread(task);
        Thread thread5 = new Thread(task);

        thread4.start();
        thread5.start();

        // Финальный аккорд Главного потока
        System.out.println("\n>>> Главный поток продолжает работу и скоро завершится <<<");
    }
}