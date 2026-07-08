package homework.week_7;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectFactory {

    // 1. Создаем объект "из воздуха" с помощью РЕФЛЕКСИИ
    // Мы передаем паспорт (clazz), а фабрика собирает по нему объект
    public static <T> T createInstance(Class<T> clazz) {
        try {
            // Магия рефлексии: "Найди пустой конструктор этого класса и вызови его"
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать объект класса " + clazz.getName(), e);
        }
    }

    // 2. Создаем список из любого количества элементов
    // Загадочное Т... означает, что мы можем передать сюда сколько угодно элементов через запятую!
    @SafeVarargs
    public static <T> List<T> createListWithElements(T... elements) {
        // Arrays.asList собирает наши переданные элементы, а ArrayList делает список изменяемым
        return new ArrayList<>(Arrays.asList(elements));
    }

    // 3. Превращаем список обратно в массив (снова нужна рефлексия для создания массива)
    @SuppressWarnings("unchecked")
    public static <T> T[] createArrayFromList(List<T> list, Class<T> type) {
        // Просим систему создать пустой массив нужного размера по паспорту 'type'
        T[] emptyArray = (T[]) Array.newInstance(type, list.size());
        // Встроенный метод списка, который высыпает свои данные в наш пустой массив
        return list.toArray(emptyArray);
    }

    // 4. Безопасное копирование списка (чтобы случайно не испортить оригинал)
    public static <T> List<T> copyList(List<T> original) {
        return new ArrayList<>(original);
    }

    // 5. Удобное создание Пары (как мы делали с Triple.create)
    public static <T, U> Pair<T, U> createPair(T first, U second) {
        return new Pair<>(first, second);
    }
}

// Наша коробочка для двух любых типов данных (T и U)
record Pair<T, U>(T first, U second) {
}
class FactoryDemo {
    public static void main(String[] args) {

        // --- Тест 1: Создание объекта через рефлексию ---
        // Просим фабрику: "Сделай мне пустой объект типа Object"
        Object myObj = ObjectFactory.createInstance(Object.class);
        System.out.println("1. Успешно создан объект класса: " + myObj.getClass().getSimpleName());

        // --- Тест 2: Создание списка "на лету" ---
        // Смотри, как удобно: мы просто перечисляем числа через запятую!
        List<Integer> numbers = ObjectFactory.createListWithElements(10, 20, 30, 40, 50);
        System.out.println("2. Создан список: " + numbers);

        // --- Тест 3: Превращение списка в массив ---
        // Передаем наш список и паспорт (Integer.class), чтобы фабрика знала, какой массив строить
        Integer[] array = ObjectFactory.createArrayFromList(numbers, Integer.class);
        System.out.println("3. Из списка сделан массив длиной: " + array.length);

        // --- Тест 4: Копирование ---
        List<Integer> copiedNumbers = ObjectFactory.copyList(numbers);
        System.out.println("4. Копия списка: " + copiedNumbers);

        // --- Тест 5: Создание Пары ---
        // Создаем пару: Название товара (String) и его цена (Double)
        Pair<String, Double> product = ObjectFactory.createPair("Ноутбук", 1500.50);
        System.out.println("5. Создана пара: " + product.first() + " стоит $" + product.second());
    }
}