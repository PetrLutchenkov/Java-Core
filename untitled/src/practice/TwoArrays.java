package practice;

public class TwoArrays {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3};
        int[] array2 = {10, 20, 30};
        int[] result = new int[3];

        // Параллельная обработка двух массивов
        for (int i = 0, j = 0; i < array1.length; i++, j++) {
            result[i] = array1[i] + array2[j];
        }

        // Вывод результата
        for (int i = 0; i < result.length; i++) {
            System.out.println("result[" + i + "] = " + result[i]);
        }
    }
}
