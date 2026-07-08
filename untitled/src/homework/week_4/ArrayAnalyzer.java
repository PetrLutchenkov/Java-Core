package homework.week_4;

/*
        Задание 2.1: Анализатор массива
        Используя цикл for:
        1. Найдите максимальный и минимальный элементы
        2. Посчитайте сумму всех элементов
        3. Найдите среднее арифметическое
        4. Посчитайте количество четных чисел
 */

public class ArrayAnalyzer {
    public static void main(String[] args) {
        int[] numbers = {3, 7, 2, 8, 1, 9, 4, 6, 5};

        if (numbers.length == 0) {
            System.out.println("Массив пуст, анализ невозможен.");
            return;
        }

        int max = numbers[0];
        int min = numbers[0];
        int sum = 0;
        int evenCount = 0;

        for (int i = 0; i < numbers.length; i++) {
            int current = numbers[i];

            if (current > max) max = current;
            if (current < min) min = current;

            sum += current;

            if (current % 2 == 0) {
                evenCount++;
            }
        }

        double average = (double) sum / numbers.length;

        System.out.println("=== Анализ массива ===");
        System.out.println("Максимум: " + max);
        System.out.println("Минимум: " + min);
        System.out.println("Сумма: " + sum);
        System.out.printf("Среднее арифметическое: %.2f%n", average);
        System.out.println("Количество четных чисел: " + evenCount);
    }
}