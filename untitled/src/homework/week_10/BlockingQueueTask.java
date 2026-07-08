package homework.week_10;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom; // Для удобной генерации случайных задержек

public class BlockingQueueTask {

    public static void main(String[] args) {
        // Создаем потокобезопасную очередь с лимитом в 5 элементов
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // --- СОЗДАНИЕ ПРОИЗВОДИТЕЛЯ (PRODUCER) ---
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    // Метод put() заблокирует поток, если очередь уже содержит 5 элементов
                    queue.put(i);
                    System.out.println("[Producer] Добавил: " + i + " | Элементов в очереди: " + queue.size());

                    // Генерируем случайную задержку от 300 до 700 мс
                    int sleepTime = ThreadLocalRandom.current().nextInt(300, 701);
                    Thread.sleep(sleepTime);
                }
                System.out.println("[Producer] Закончил работу.");
            } catch (InterruptedException e) {
                System.out.println("[Producer] Был прерван.");
                Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
            }
        });

        // --- СОЗДАНИЕ ПОТРЕБИТЕЛЯ (CONSUMER) ---
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    // Генерируем случайную задержку от 800 до 1200 мс (Consumer медленнее)
                    int sleepTime = ThreadLocalRandom.current().nextInt(800, 1201);
                    Thread.sleep(sleepTime);

                    // Метод take() извлечет элемент. Если очередь пуста, поток заблокируется.
                    Integer value = queue.take();
                    System.out.println("   [Consumer] Забрал: " + value + " | Осталось в очереди: " + queue.size());
                }
                System.out.println("   [Consumer] Закончил работу.");
            } catch (InterruptedException e) {
                System.out.println("[Consumer] Был прерван.");
                Thread.currentThread().interrupt();
            }
        });

        // Запускаем оба потока
        producer.start();
        consumer.start();
    }
}