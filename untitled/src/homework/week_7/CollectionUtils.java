package homework.week_7;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CollectionUtils {

    public static <T> Optional<T> getFirstElement(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(collection.iterator().next());
    }

    public static <T> List<T> mergeLists(Collection<T> first, Collection<T> second) {
        List<T> merged = new ArrayList<>();
        if (first != null) merged.addAll(first);
        if (second != null) merged.addAll(second);
        return merged;
    }

    public static <T> List<T> filterByType(Collection<Object> collection, Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Object item : collection) {
            if (type.isInstance(item)) {
                result.add(type.cast(item)); // Безопасное приведение
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> type, int size) {
        try {
            return (T[]) Array.newInstance(type, size);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания массива", e);
        }
    }

    public static <T> void swap(T[] array, int i, int j) {
        if (array != null && i >= 0 && j >= 0 && i < array.length && j < array.length) {
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}

class CollectionUtilsDemo {
    public static void main(String[] args) {

        // --- 1. Тестируем получение первого элемента ---
        List<String> list1 = List.of("Яблоко", "Банан");
        List<String> list2 = List.of("Вишня", "Дыня");

        // Если в list1 что-то есть, выведет это. Если он пуст, сработает orElse и выведет "Пусто!"
        System.out.println("Первый элемент: " + CollectionUtils.getFirstElement(list1).orElse("Пусто!"));


        // --- 2. Тестируем слияние двух списков ---
        // Должен получиться один большой список из 4 фруктов
        System.out.println("Объединенный список: " + CollectionUtils.mergeLists(list1, list2));


        // --- 3. Тестируем фильтрацию по типу ---
        // У нас есть список с "кашей" из разных типов данных (Строки, Целые числа, Дробные числа)
        List<Object> mixed = List.of("Текст1", 100, "Текст2", 3.14);

        // Просим нашу утилиту достать из этой каши ТОЛЬКО Строки (String.class)
        System.out.println("Отфильтровано только строки: " + CollectionUtils.filterByType(mixed, String.class));


        // --- 4. Тестируем создание массива и метод swap (смена мест) ---
        // Создаем массив строк размером 2 ячейки
        String[] arr = CollectionUtils.createArray(String.class, 2);
        arr[0] = "Первый";
        arr[1] = "Второй";

        // Меняем нулевой и первый элементы местами
        CollectionUtils.swap(arr, 0, 1);

        System.out.println("Массив после swap (смены мест): [" + arr[0] + ", " + arr[1] + "]");
    }
}