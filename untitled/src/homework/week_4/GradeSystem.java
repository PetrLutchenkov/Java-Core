package homework.week_4;

/*
        Задание 1.1: Система оценки студентов
        Реализуйте систему оценок:
        90-100: "Отлично"
        75-89: "Хорошо"
        60-74: "Удовлетворительно"
        0-59: "Неудовлетворительно"
        Проверка корректности ввода (0-100)
 */

public class GradeSystem {
    public static void main(String[] args) {
        int score = 85;

        System.out.println("=== Система оценок ===");
        System.out.println("Введенный балл: " + score);

        if (score < 0 || score > 100) {
            System.out.println("Ошибка: Балл должен быть в диапазоне от 0 до 100.");
        }
        else if (score >= 90) {
            System.out.println("Результат: Отлично");
        } else if (score >= 75) {
            System.out.println("Результат: Хорошо");
        } else if (score >= 60) {
            System.out.println("Результат: Удовлетворительно");
        } else {
            System.out.println("Результат: Неудовлетворительно");
        }
    }
}