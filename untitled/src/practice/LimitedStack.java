package practice;

public class LimitedStack extends SimpleStack {

    private final int maxSize;

    public LimitedStack(int capacity, int maxSize) {
        // Вызываем конструктор SimpleStack, который создаст базовый массив
        super(capacity);

        // ТЗ: maxSize > 0
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Максимальный размер (maxSize) должен быть больше 0!");
        }
        // ТЗ: maxSize >= capacity (глупо делать стартовый массив больше, чем максимальный лимит)
        if (maxSize < capacity) {
            throw new IllegalArgumentException("maxSize не может быть меньше стартовой вместимости (capacity)!");
        }

        this.maxSize = maxSize;
    }

    @Override
    public void push(int value) {
        // ТЗ: Если size() >= maxSize → выбросить IllegalStateException.
        // Используем метод size(), который мы унаследовали аж от AbstractStack!
        if (size() >= maxSize) {
            throw new IllegalStateException("Стек переполнен! Достигнут лимит в " + maxSize + " элементов.");
        }

        // ТЗ: Иначе вызвать super.push(value).
        // Если проверка пройдена, просто отдаем работу родительскому классу SimpleStack
        super.push(value);
    }

    static void main() {
        SimpleStack stack = new LimitedStack(2,6);
        stack.push(4);
        stack.push(110);
        stack.push(30);
        stack.push(42);
    }
}