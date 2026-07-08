package practice;

import java.util.Scanner;

public class While_true {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите число (или 'exit' для выхода):");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break; // Немедленно выходим из цикла
            }
            System.out.println("Вы ввели: " + input);
        }

    }
}
