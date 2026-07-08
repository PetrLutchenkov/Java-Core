package homework.week_4;

/*
        Дополнительное задание: Обработка двумерного массива
        Реализуйте обработку двумерного массива:
        1. Выведите матрицу в читаемом виде
        2. Найдите сумму всех элементов
        3. Найдите сумму элементов главной диагонали
        4. Транспонируйте матрицу
 */

public class MatrixProcessor {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("=== Исходная матрица ===");
        printMatrix(matrix);

        int totalSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                totalSum += matrix[i][j];
            }
        }
        System.out.println("\nСумма всех элементов: " + totalSum);

        int diagonalSum = 0;
        int size = Math.min(matrix.length, matrix[0].length);
        for (int i = 0; i < size; i++) {
            diagonalSum += matrix[i][i];
        }
        System.out.println("Сумма главной диагонали: " + diagonalSum);

        System.out.println("\n=== Транспонированная матрица ===");
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        printMatrix(transposed);
    }

    private static void printMatrix(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.printf("%3d ", mat[i][j]);
            }
            System.out.println();
        }
    }
}