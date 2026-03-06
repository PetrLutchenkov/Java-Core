package practice;

public class PerformanceOptimization {
    public static void main(String[] args) {
        int[] numbers = new int[1000000];
        // Заполняем массив случайными числами
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (int) (Math.random() * 1000);
        }

        int target = 42;
        boolean found = false;
        long startTime = System.currentTimeMillis();

        // Поиск с break - останавливается при нахождении
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                found = true;
                break; // Немедленный выход при нахождении
            }
        }

        long endTime = System.currentTimeMillis();

        if (found) {
            System.out.println("Число " + target + " найдено за " + (endTime - startTime) + " мс");
        } else {
            System.out.println("Число не найдено");
        }
    }
}
