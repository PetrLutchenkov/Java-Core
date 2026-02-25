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

        // Тестируем примеры из вашей задачи
        System.out.println("Результат 10 * 5: " + calculate("10 * 5"));
        System.out.println("Результат 100 / 20: " + calculate("100 / 20"));

        // Тестируем ошибки (выведут сообщение и вернут 0.0)
        calculate("15 +");
        calculate("a + 2");
        calculate("10 ** 2");
    }

    public static double calculate(String input) {
        // 1. Очищаем строку и режем её по пробелам
        // \\s+ — это регулярное выражение для "одного или нескольких пробелов"
        String[] parts = input.trim().split("\\s+");

        // 2. ВАЛИДАЦИЯ: Ровно два операнда и один знак (итого 3 части)
        if (parts.length != 3) {
            System.out.println("Ошибка валидации: '" + input + "' -> должно быть 3 элемента (число знак число)");
            return 0.0;
        }

        // 3. Пытаемся достать данные из массива
        String firstPart = parts[0];
        String operator = parts[1];
        String secondPart = parts[2];

        // 4. ВАЛИДАЦИЯ: Проверяем, что числа — это действительно цифры
        if (!isNumeric(firstPart) || !isNumeric(secondPart)) {
            System.out.println("Ошибка валидации: '" + input + "' -> операнды должны быть целыми числами");
            return 0.0;
        }

        int a = Integer.parseInt(firstPart);
        int b = Integer.parseInt(secondPart);

        // 5. ВАЛИДАЦИЯ и РАСЧЕТ: Проверяем знак и считаем
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

        // Если дошли сюда, значит знак был невалидным (например "**")
        System.out.println("Ошибка валидации: '" + input + "' -> оператор '" + operator + "' не поддерживается");
        return 0.0;
    }

    // Вспомогательный метод для проверки, является ли строка числом (без циклов)
    public static boolean isNumeric(String str) {
        return str.matches("\\d+"); // Проверяет, состоит ли строка только из цифр
    }
}