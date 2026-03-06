package homework.week_4;

/*
        Задание 2.2: Генератор таблиц
        Сгенерируйте таблицу умножения 10x10
        Используйте вложенные циклы for
        Форматируйте вывод в виде аккуратной таблицы
 */

public class TableGenerator {
    public static void main(String[] args) {
        System.out.println("=== ТАБЛИЦА УМНОЖЕНИЯ 10x10 ===");

        // 1. Печатаем заголовок столбцов
        System.out.print("    |");
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%4d", i);
        }
        System.out.println("\n---------------------------------------------");

        for (int row = 1; row <= 10; row++) {
            System.out.printf("%3d |", row);

            for (int col = 1; col <= 10; col++) {
                System.out.printf("%4d", row * col);
            }
            System.out.println();
        }
    }
}