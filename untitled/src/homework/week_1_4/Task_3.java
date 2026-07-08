package homework.week_1_4;

public class Task_3 {
    public static void main(String[] args) {

        /*
        Парсер простых выражений
        Написать метод, который принимает строку вида "15 + 27" и:
        Валидирует строку по правилам:
            Ровно два операнда.
            Операнды — целые числа.
            Оператор — один из + - * /.
        Выполняет вычисление и возвращает результат.
            Валидные строки: "10 * 5", "100 / 20".
            Невалидные: "15 +", "a + 2", "10 ** 2".
         */

        System.out.println("Результат 10 * 5: " + calculate("10 * 5"));
        System.out.println("Результат 100 / 20: " + calculate("100 / 20"));

        calculate("15 +");
        calculate("a + 2");
        calculate("10 ** 2");
    }

    public static double calculate(String input) {
        String[] parts = input.trim().split("\\s+");

        if (parts.length != 3) {
            System.out.println("Ошибка валидации: '" + input + "' -> должно быть 3 элемента (число знак число)");
            return 0.0;
        }

        String firstPart = parts[0];
        String operator = parts[1];
        String secondPart = parts[2];

        if (!isNumeric(firstPart) || !isNumeric(secondPart)) {
            System.out.println("Ошибка валидации: '" + input + "' -> операнды должны быть целыми числами");
            return 0.0;
        }

        int a = Integer.parseInt(firstPart);
        int b = Integer.parseInt(secondPart);

        if (operator.equals("+")) return a + b;
        if (operator.equals("-")) return a - b;
        if (operator.equals("*")) return a * b;
        if (operator.equals("/")) {
            if (b == 0) {
                System.out.println("Ошибка: деление на ноль!");
                return 0.0;
            }
            return (double) a / b;
        }
        System.out.println("Ошибка валидации: '" + input + "' -> оператор '" + operator + "' не поддерживается");
        return 0.0;
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}