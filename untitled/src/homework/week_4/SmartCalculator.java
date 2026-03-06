package homework.week_4;

/*
        Задание 1.2: Умный калькулятор
        Реализуйте калькулятор с проверками:
        - Поддержка операций: +, -, *, /, %
        - Проверка деления на ноль
        - Обработка неизвестных операций
        - Использование switch-case
 */

public class SmartCalculator {
    public static void main(String[] args) {
        double num1 = 10;
        double num2 = 5;
        String operation = "divide"; // Поддерживаем слова или символы

        System.out.println("\n=== Умный калькулятор ===");

        switch (operation.toLowerCase()) {
            case "add":
            case "+":
                System.out.println("Результат: " + (num1 + num2));
                break;
            case "subtract":
            case "-":
                System.out.println("Результат: " + (num1 - num2));
                break;
            case "multiply":
            case "*":
                System.out.println("Результат: " + (num1 * num2));
                break;
            case "divide":
            case "/":
                if (num2 == 0) {
                    System.out.println("Ошибка: Деление на ноль невозможно!");
                } else {
                    System.out.println("Результат: " + (num1 / num2));
                }
                break;
            case "modulo":
            case "%":
                if (num2 == 0) {
                    System.out.println("Ошибка: Деление на ноль невозможно!");
                } else {
                    System.out.println("Результат: " + (num1 % num2));
                }
                break;
            default:
                System.out.println("Ошибка: Неизвестная операция '" + operation + "'.");
        }
    }
}