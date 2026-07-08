package homework.week_9;

/**
 * ПРАКТИКУМ: АЛГОРИТМЫ СОРТИРОВКИ И ПОИСКА
 * Реализация и тестирование основных алгоритмов поиска и сортировки
 */
public class SortingSearchingAlgorithms {

    // Задание 1: Линейный поиск
    public int linearSearch(int[] array, int target) {
        // Проверка на null или пустой массив
        if (array == null || array.length == 0) {
            return -1;
        }

        // Проходим по всем элементам массива
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i; // Возвращаем индекс найденного элемента
            }
        }
        return -1; // Элемент не найден
    }

    // Задание 2: Бинарный поиск (для отсортированного массива)
    public int binarySearch(int[] array, int target) {
        // Проверка на null или пустой массив
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // Избегаем переполнения

            if (array[mid] == target) {
                return mid; // Элемент найден
            } else if (array[mid] < target) {
                left = mid + 1; // Ищем в правой половине
            } else {
                right = mid - 1; // Ищем в левой половине
            }
        }
        return -1; // Элемент не найден
    }

    // Задание 3: Сортировка вставками
    public void insertionSort(int[] array) {
        // Проверка на null или массив из одного элемента
        if (array == null || array.length <= 1) {
            return;
        }

        // Начинаем с первого элемента (индекс 1)
        for (int i = 1; i < array.length; i++) {
            int key = array[i]; // Текущий элемент для вставки
            int j = i - 1;

            // Сдвигаем элементы больше key вправо
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key; // Вставляем key на правильное место
        }
    }

    // Задание 4: Сортировка слиянием (внешний метод)
    public void mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        mergeSort(array, 0, array.length - 1);
    }

    // Рекурсивная реализация сортировки слиянием
    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2; // Избегаем переполнения

            // Рекурсивно сортируем левую и правую части
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);

            // Сливаем отсортированные части
            merge(array, left, mid, right);
        }
    }

    // Метод слияния двух отсортированных подмассивов
    private void merge(int[] array, int left, int mid, int right) {
        // Создаём временные массивы для левой и правой частей
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];

        // Копируем данные во временные массивы
        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        // Сливаем временные массивы обратно в основной
        int i = 0, j = 0, k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Копируем оставшиеся элементы (если есть)
        while (i < leftSize) {
            array[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < rightSize) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // Тестирование всех алгоритмов
    public static void main(String[] args) {
        SortingSearchingAlgorithms algorithms = new SortingSearchingAlgorithms();

        // Тестовые данные
        int[] testArray = {64, 34, 25, 12, 22, 11, 90, 5};
        int[] sortedArray = {5, 11, 12, 22, 25, 34, 64, 90};

        System.out.println("=== ТЕСТИРОВАНИЕ АЛГОРИТМОВ ===");

        // Тест линейного поиска
        System.out.println("Линейный поиск 22: " + algorithms.linearSearch(testArray, 22));
        System.out.println("Линейный поиск 100: " + algorithms.linearSearch(testArray, 100));

        // Тест бинарного поиска
        System.out.println("Бинарный поиск 25: " + algorithms.binarySearch(sortedArray, 25));
        System.out.println("Бинарный поиск 100: " + algorithms.binarySearch(sortedArray, 100));

        // Тест сортировки вставками
        int[] insertionArray = testArray.clone();
        algorithms.insertionSort(insertionArray);
        System.out.print("Сортировка вставками: ");
        for (int num : insertionArray) {
            System.out.print(num + " ");
        }
        System.out.println();

        // Тест сортировки слиянием
        int[] mergeArray = testArray.clone();
        algorithms.mergeSort(mergeArray);
        System.out.print("Сортировка слиянием: ");
        for (int num : mergeArray) {
            System.out.print(num + " ");
        }
        System.out.println();

        // Тест граничных случаев
        System.out.println("\n=== ТЕСТ ГРАНИЧНЫХ СЛУЧАЕВ ===");
        int[] emptyArray = {};
        int[] singleElement = {42};

        System.out.println("Поиск в пустом массиве: " + algorithms.linearSearch(emptyArray, 5));
        System.out.println("Поиск в массиве из одного элемента: " + algorithms.linearSearch(singleElement, 42));

        // Сравнение производительности
        System.out.println("\n=== СРАВНЕНИЕ СЛОЖНОСТИ ===");
        System.out.println("Линейный поиск: O(n)");
        System.out.println("Бинарный поиск: O(log n)");
        System.out.println("Сортировка вставками: O(n²)");
        System.out.println("Сортировка слиянием: O(n log n)");

        // Рекомендации по выбору алгоритма
        System.out.println("\n=== РЕКОМЕНДАЦИИ ===");
        System.out.println("• Используйте линейный поиск для неотсортированных данных");
        System.out.println("• Используйте бинарный поиск для отсортированных данных");
        System.out.println("• Сортировка вставками эффективна для маленьких массивов (< 50 элементов)");
        System.out.println("• Сортировка слиянием — универсальный выбор для больших массивов");
    }
}
