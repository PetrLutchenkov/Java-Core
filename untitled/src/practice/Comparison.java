package practice;

public class Comparison {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};

        // Традиционный for
        System.out.println("Традиционный for:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Элемент " + i + ": " + numbers[i]);
        }

        // For-each
        System.out.println("\nFor-each:");
        for (int number : numbers) {
            System.out.println("Элемент: " + number);
        }
    }
}
