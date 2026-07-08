package practice;

public class SimpleStack extends AbstractStack {

    private static final int DEFAULT_CAPACITY = 5;

    // Конструктор по умолчанию (без параметров)
    public SimpleStack() {
        // ТЗ: Использует DEFAULT_CAPACITY.
        // Вызываем конструктор родителя (AbstractStack) и передаем ему дефолтное значение
        super(DEFAULT_CAPACITY);
    }

    // Конструктор с указанием размера
    public SimpleStack(int capacity) {
        super(capacity); // Уже реализовано верно
    }

    // ================= РЕАЛИЗАЦИЯ МЕТОДОВ =================

    @Override
    public void push(int value) {
        // 1. Перед добавлением ОБЯЗАТЕЛЬНО проверяем, есть ли место в массиве
        // Если места нет, этот метод родителя увеличит массив в 2 раза
        ensureCapacity();

        // 2. Кладем значение в первую свободную ячейку.
        // size сейчас указывает как раз на индекс пустой ячейки (так как индексация с 0)
        elements[size] = value;

        // 3. Увеличиваем счетчик элементов
        size++;

        // Лайфхак: шаги 2 и 3 профессионалы часто пишут в одну строчку:
        // elements[size++] = value;
    }

    // Перегруженный метод для добавления сразу нескольких элементов
    public void push(int... values) {
        // ТЗ: values не null
        if (values == null) {
            throw new IllegalArgumentException("Массив значений не может быть null!");
        }

        // ТЗ: для каждого значения вызвать push(int)
        // Проходимся циклом for-each по всем переданным значениям
        for (int value : values) {
            this.push(value); // Вызываем наш собственный метод push(int), написанный выше
        }
    }
}