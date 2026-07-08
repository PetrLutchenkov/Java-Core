package homework.week_1_4;

import java.util.Scanner;

public class Week_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /*
        Задание 1 (Три цифры в число):
        Пользователь вводит с клавиатуры три цифры. Необходимо создать число, содержащее эти цифры.
        Например, если с клавиатуры введено 7, 3, 8, тогда нужно сформировать число 738.
         */

        System.out.println("=== Задание 1: Три цифры в число ===");
        System.out.print("Введите первую цифру (сотни): ");
        int d1 = scanner.nextInt();
        System.out.print("Введите вторую цифру (десятки): ");
        int d2 = scanner.nextInt();
        System.out.print("Введите третью цифру (единицы): ");
        int d3 = scanner.nextInt();
        int finalNumber = d1 * 100 + d2 * 10 + d3;
        System.out.println("Сформированное число: " + finalNumber + "\n");

        /*
        Задание 2 (Произведение цифр числа):
        Пользователь вводит с клавиатуры число, состоящее из четырех цифр.
        Требуется найти произведение цифр. Например, если с клавиатуры введено 1324,
        тогда результат произведения 1 * 3 * 2 * 4 = 24.
         */

        System.out.println("=== Задание 2: Произведение цифр числа ===");
        System.out.print("Введите четырехзначное число (например, 1324): ");
        int number = scanner.nextInt();
        int n4 = number % 10;
        int n3 = (number / 10) % 10;
        int n2 = (number / 100) % 10;
        int n1 = (number / 1000) % 10;
        int rezult = n1 * n2 * n3 * n4;
        System.out.println("Произведение цифр: " + rezult + "\n");

        /*
        Задание 3 (Перевод метров): Пользователь вводит с клавиатуры количество метров.
        Требуется вывести результат перевода метров в сантиметры, дециметры, миллиметры, мили.
         */

        System.out.println("=== Задание 3: Перевод метров ===");
        System.out.print("Введите количество метров: ");
        double meters = scanner.nextDouble();
        System.out.println("В дециметрах: " + (meters * 10));
        System.out.println("В сантиметрах: " + (meters * 100));
        System.out.println("В миллиметрах: " + (meters * 1000));
        System.out.println("В милях: " + (meters / 1609.344) + "\n");

        /*
        Задание 4 (Площадь треугольника): Напишите программу, вычисляющую площадь треугольника.
        Пользователь с клавиатуры вводит размер основания треугольника и размер высоты.
         */

        System.out.println("=== Задание 4: Площадь треугольника ===");
        System.out.print("Введите основание треугольника: ");
        double base = scanner.nextDouble();
        System.out.print("Введите высоту треугольника: ");
        double height = scanner.nextDouble();
        double area = 0.5 * base * height;
        System.out.println("Площадь треугольника: " + area + "\n");

        /*
        Задание 5 (Целочисленное деление, остаток, корень):
        В переменных х и y хранятся два натуральных числа. Создайте программу, выводящую на консоль:
        результат целочисленного деления x на y; остаток от деления x на y; квадратный корень x.
         */

        System.out.println("=== Задание 5: Деление, остаток, корень ===");
        int x = 25;
        int y = 7;
        System.out.println("Число x = " + x + ", число y = " + y);
        System.out.println("Целочисленное деление (x / y): " + (x / y));
        System.out.println("Остаток от деления (x % y): " + (x % y));
        System.out.println("Квадратный корень из x: " + Math.sqrt(x) + "\n");

        /*
        Задание 6 (Округление числа):
        В переменной n хранится вещественное число, с ненулевой дробной частью.
        Создайте программу, округляющую число n до ближайшего
        целого и выводящую результат округления на экран.
         */

        System.out.println("=== Задание 6: Округление числа ===");
        double n = 15.67;
        long roundedN = Math.round(n);
        System.out.println("Исходное число: " + n);
        System.out.println("Округленное число: " + roundedN + "\n");

        /*
        Задание 7 (Площадь и длина окружности):
        Подсчитать площадь и длину окружности для круга с радиусом R.
        Радиус должен быть задан константой в программе. Вывести результат на консоль.
         */

        System.out.println("=== Задание 7: Площадь и длина окружности ===");
        final double R = 10.0;
        double circleArea = Math.PI * R * R;
        double circumference = 2 * Math.PI * R;
        System.out.println("Для радиуса R = " + R + ":");
        System.out.println("Площадь круга: " + circleArea);
        System.out.println("Длина окружности: " + circumference + "\n");

        /*
        Задание 8 (Пирожки и молоко для школьников): Ученикам первого класса дают 1 пирожок.
        Если вес первоклассника менее 30 кг, дополнительно дают 1 стакан молока и ещё 1 пирожок.
        В первых классах школы учится n учеников.
        Стакан молока имеет емкость 200 мл, а упаковка молока – 0,9 л.
        Написать программу, которая определит количество пакетов молока и пирожков,
        необходимых каждый день для условий:

            если в школе 100% всех учеников, у которых вес меньше 30 кг;
            если в школе 60% учеников имеют вес меньше 30 кг.
            если в школе 1% учеников имеют вес меньше 30 кг.
            (!!!) Учесть, что нельзя купить два с половиной пакета молока; можно купить два или три.
         */

        System.out.println("=== Задание 8: Пирожки и молоко ===");
        System.out.print("Введите общее количество учеников в первых классах (n): ");
        int totalStudents = scanner.nextInt();
        calculateFood(totalStudents, 100);
        calculateFood(totalStudents, 60);
        calculateFood(totalStudents, 1);

        // Закрываем сканнер
        scanner.close();
    }

    // Вспомогательный метод для Задачи 8, чтобы не дублировать код три раза
    public static void calculateFood(int totalStudents, double percentage) {
        // Вычисляем, сколько детей весят меньше 30 кг
        int lightStudents = (int) Math.round(totalStudents * (percentage / 100.0));

        // Каждому ученику по пирожку + еще одному пирожку для тех, кто < 30 кг
        int totalPies = totalStudents + lightStudents;

        // Молоко нужно только худеньким (по 200 мл на человека)
        int totalMilkMl = lightStudents * 200;

        // В одном пакете 0.9 литра = 900 мл.
        // Math.ceil округляет ВВЕРХ (если нужно 1000 мл, купим 2 пакета по 900)
        int milkPacks = (int) Math.ceil((double) totalMilkMl / 900.0);

        System.out.println("Если легких детей " + percentage + "% (это " + lightStudents + " чел.):");
        System.out.println(" - Нужно пирожков: " + totalPies);
        System.out.println(" - Нужно пакетов молока: " + milkPacks);
    }
}