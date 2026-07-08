package practice;

public class StepTraversal {
    public static void main(String[] args) {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

        // Обход каждого второго элемента
        System.out.println("Обход с шагом 2:");
        for (int i = 0; i < letters.length; i += 2) {
            System.out.println("Элемент " + i + ": " + letters[i]);
        }
    }
}
