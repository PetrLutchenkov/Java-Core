package homework.week_10;

import java.util.concurrent.atomic.AtomicInteger;

public class CountAtomicInteger {
    private final AtomicInteger count = new AtomicInteger(0);
    public int getCount() {
        return count.get();
    }
    void increment(){
        count.incrementAndGet();
    }
}
class Test {
    static void main() throws InterruptedException {
        CountAtomicInteger counter = new CountAtomicInteger();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                counter.increment();
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                counter.increment();
            }
        });
        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                counter.increment();
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println(counter.getCount());
    }

}