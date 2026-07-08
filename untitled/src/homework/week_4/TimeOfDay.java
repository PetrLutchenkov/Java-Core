package homework.week_4;

/*
        Задание 1.3: Определитель времени суток
        Определите время суток:
        6-11: Утро
        12-17: День
        18-23: Вечер
        0-5: Ночь
        Проверка корректности часа (0-23)
 */

public class TimeOfDay {
    public static void main(String[] args) {
        int hour = 14;

        System.out.println("\n=== Определитель времени суток ===");

        if (hour < 0 || hour > 23) {
            System.out.println("Ошибка: Некорректный час. Введите от 0 до 23.");
        } else if (hour >= 6 && hour <= 11) {
            System.out.println("Сейчас Утро.");
        } else if (hour >= 12 && hour <= 17) {
            System.out.println("Сейчас День.");
        } else if (hour >= 18 && hour <= 23) {
            System.out.println("Сейчас Вечер.");
        } else {
            System.out.println("Сейчас Ночь.");
        }
    }
}