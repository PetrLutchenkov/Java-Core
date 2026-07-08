package homework.week_10;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SharedBuffer {
    // Наш склад на 5 мест
    private final List<Integer> buffer = new ArrayList<>();
    private final int MAX_SIZE = 5;

    // Метод для Производителя
    public synchronized void produce(int value) throws InterruptedException {
        // ТВОЙ КОД:
        // 1. Пока размер буфера (buffer.size()) равен MAX_SIZE, уходи в wait()
        while (buffer.size() == MAX_SIZE) {
            this.wait();
        }
        // 2. Добавь элемент в буфер: buffer.add(value);
        buffer.add(value);

        System.out.println("Произвел: " + value + " | На складе: " + buffer.size());

        // 3. Разбуди Потребителя (лучше использовать notifyAll())
        notifyAll();
    }

    // Метод для Потребителя
    public synchronized int consume() throws InterruptedException {
        // ТВОЙ КОД:
        // 1. Пока буфер пустой (buffer.isEmpty()), уходи в wait()
        while (buffer.isEmpty()) {
            this.wait();
        }
        // 2. Достань и удали самый первый элемент из списка
        int value = buffer.remove(0);
        System.out.println("Потребил: " + value + " | На складе: " + buffer.size());

        // 3. Разбуди Производителя (освободилось место!)
        notifyAll();

        // 4. Верни значение
        return value;
    }

    static void main() {
        // 1. Создаем наш склад (ОДИН общий объект для обоих потоков!)
        SharedBuffer buffer = new SharedBuffer();
        Random random = new Random();

        // 2. Создаем поток Производителя
        Thread producer = new Thread(() -> {
            try {
                while (true) {
                    // Генерируем случайное число от 0 до 99
                    int value = random.nextInt(100);
                    // Кладем на склад
                    buffer.produce(value);
                    // Спим полсекунды, чтобы успевать читать консоль
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 3. Создаем поток Потребителя
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    // ТВОЙ КОД ЗДЕСЬ:
                    // 1. Вызови метод consume() у нашего buffer и сохрани результат в переменную
                    int res = buffer.consume();

                    // 2. Усыпи поток на 800 миллисекунд (Thread.sleep(800)),
                    Thread.sleep(800);
                    // чтобы Потребитель работал чуть медленнее Производителя.

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 4. Запускаем оба потока
        producer.start();
        consumer.start();
    }
}