package practice;

public abstract class AbstractStack {

    protected int[] elements;
    protected int size;

    // Конструктор
    public AbstractStack(int capacity) {
        // ТЗ: capacity > 0. Если нет — бросаем ошибку, так как массив не может быть отрицательным или нулевым.
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимость (capacity) должна быть больше 0");
        }
        // Инициализируем массив и обнуляем размер
        this.elements = new int[capacity];
        this.size = 0;
    }

    // Абстрактный метод (реализуют наследники)
    public abstract void push(int value);

    // Удаляет и возвращает верхний элемент
    public int pop() {
        // ТЗ: Если стек пуст — выбросить IllegalStateException
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст! Нечего удалять.");
        }
        // Уменьшаем размер на 1.
        // Теперь size указывает на последний добавленный элемент.
        size--;

        // Возвращаем этот элемент.
        // (Очищать саму ячейку массива не обязательно, так как это примитив int,
        // и при следующем push мы просто перезапишем это место).
        return elements[size];
    }

    // Возвращает верхний элемент без удаления
    public int peek() {
        // ТЗ: Если стек пуст — выбросить IllegalStateException
        if (isEmpty()) {
            throw new IllegalStateException("Стек пуст! Не на что смотреть.");
        }
        // Просто смотрим на верхний элемент (индекс на 1 меньше размера), но size не меняем
        return elements[size - 1];
    }

    // Проверка на пустоту
    public boolean isEmpty() {
        return size == 0;
    }

    // Возвращает текущее количество элементов
    public int size() {
        return size;
    }

    // Увеличение массива (уже было реализовано отлично)
    protected void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length * 2;
            int[] newArray = new int[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }
    }
}