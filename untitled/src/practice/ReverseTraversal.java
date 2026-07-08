package practice;

public class ReverseTraversal {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};

        // Обратный обход от последнего к первому элементу
        System.out.println("Обратный обход:");
        for (int i = numbers.length - 1; i >= 0; i--) {
            System.out.println("Элемент " + i + ": " + numbers[i]);
        }
    }
}
