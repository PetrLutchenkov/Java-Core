package homework;

import java.util.Random;

public class Task_5 {
    public static void main(String[] args) {

    /*
    Небольшая задачка на закрепление
        Вы - метеоролог, который ведет ежедневные наблюдения за температурой.
    Вам нужно проанализировать температурные данные за неделю.
        Создайте массив temperatures для хранения температур за 7 дней недели.
    Заполните его произвольными значениями в диапазоне от -10 до +30 градусов.
    1.Напишите программу, которая: Выводит все температуры недели
    2.Находит и выводит самую высокую температуру
    3.Находит и выводит самую низкую температуру
    4.Рассчитывает и выводит среднюю температуру за неделю
    Усложнение (по желанию): Подсчитайте количество дней с температурой выше 20 градусов.
    Найдите разницу между самой высокой и самой низкой температурой.
     */

        int[] temperatures = new int [7];
        Random random = new Random();
        int min = -10;
        int max = 30;
        for (int i = 0; i < temperatures.length; i++) {
            temperatures[i] = random.nextInt(max - min + 1) + min;
        }
        int sum = 0;
        int hotDaysCount = 0;
        int maxTemp = temperatures[0];
        int minTemp = temperatures[0];

        System.out.println("=== Температуры за неделю ===");
        for (int i = 0; i < temperatures.length; i++) {
            int currentTemp = temperatures[i];
//            String temperatura = (currentTemp%10 == 2) ? " градуса" : " градусов";
            System.out.println("День " + (i + 1) + ": " + currentTemp + "°");

            sum += currentTemp;
            if (currentTemp > maxTemp) {
                maxTemp = currentTemp;
            }

            if (currentTemp < minTemp) {
                minTemp = currentTemp;
            }

            if (currentTemp > 20) {
                hotDaysCount += 1;
            }
        }

        double averageTemp = (double) sum / temperatures.length;

        int difference = maxTemp - minTemp;

        System.out.println("\n=== Анализ метеоролога ===");
        System.out.println("Самая высокая температура: " + maxTemp);
        System.out.println("Самая низкая температура: " + minTemp);
        System.out.println("Средняя температура: " + String.format("%.1f", averageTemp));
        System.out.println("Дней с температурой выше 20 градусов: " + hotDaysCount);
        System.out.println("Разница между самой высокой и самой низкой: " + difference + " градусов");
    }
}