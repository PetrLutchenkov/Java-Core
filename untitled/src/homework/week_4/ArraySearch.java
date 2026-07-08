package homework.week_4;

/*
        Задание 2.3: Поиск в массиве
        Используя for-each:
        1. Найдите все вхождения target
        2. Подсчитайте количество вхождений
        3. Используйте break при первом найденном
        4. Используйте continue для пропуска определенных элементов
 */

public class ArraySearch {
    public static void main(String[] args) {
        String[] names = {"Анна", "Борис", "Виктор", "Галина", "Дмитрий", "Анна"};
        String target = "Анна";

        System.out.println("=== Поиск в массиве ===");

        int count = 0;
        for (String name : names) {
            if (name.equals(target)) {
                count++;
            }
        }
        System.out.println("Сценарий 1: Имя '" + target + "' встречается " + count + " раз(а).");

        System.out.println("\nСценарий 2: Поиск до первого совпадения (без учета Бориса):");
        for (String name : names) {
            if (name.equals("Борис")) {
                continue;
            }
            System.out.println("Проверяем: " + name);
            if (name.equals(target)) {
                System.out.println("-> Нашли цель! Останавливаем цикл.");
                break;
            }
        }

        System.out.println("\nСценарий 3: Точные индексы (используем классический for):");
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(target)) {
                System.out.println("Имя '" + target + "' найдено на индексе: " + i);
            }
        }
    }
}
