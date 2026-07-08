package homework.week_7;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

// Наш класс принимает две буквы: K (Key - ключ) и V (Value - значение)
public class GenericCache<K, V> {

    // Внутри нашего кэша прячется обычная джавовская Map (Словарь)
    private final Map<K, V> cache = new HashMap<>();

    // 1. Положить вещь в гардероб (выдаем номерок K, вешаем вещь V)
    public void put(K key, V value) {
        cache.put(key, value);
    }

    // 2. Забрать вещь по номерку.
    // Возвращаем Optional, потому что по этому номерку могут ничего не найти!
    public Optional<V> get(K key) {
        return Optional.ofNullable(cache.get(key)); // Упаковываем результат в безопасную коробку
    }

    // 3. Хитрый метод: "Дай мне вещь по ключу, но я хочу, чтобы она точно была типом T"
    // Сюда мы передаем "паспорт" класса, как в прошлом задании!
    public <T> Optional<T> getAsType(K key, Class<T> type) {
        V value = cache.get(key); // Достаем вещь

        // Сверяем вещь с паспортом. Если совпадает — отдаем, безопасно превратив в T
        if (type.isInstance(value)) {
            return Optional.of(type.cast(value));
        }
        // Если тип не совпал (например, просили число, а там строка) — возвращаем пустоту
        return Optional.empty();
    }

    // 4. Удалить вещь (например, срок хранения истек)
    public void remove(K key) {
        cache.remove(key);
    }

    // 5. Выкинуть вообще всё из гардероба (очистить кэш)
    public void clear() {
        cache.clear();
    }

    // 6. Узнать, сколько всего вещей сейчас висит в гардеробе
    public int size() {
        return cache.size();
    }

    // 7. Получить список всех выданных номерков (ключей)
    public Set<K> getAllKeys() {
        return cache.keySet();
    }
}
class CacheDemo {
    public static void main(String[] args) {

        // Создаем кэш. Ключи будут строками (ID), а значениями - любыми объектами (Object)
        GenericCache<String, Object> userCache = new GenericCache<>();

        // Сохраняем данные (Имя и Возраст)
        userCache.put("user_1_name", "Иван");
        userCache.put("user_1_age", 25);

        // --- Тест 1: Обычное получение (get) ---
        // Пытаемся достать имя. Если нет - пишем "Не найдено"
        System.out.println("Имя: " + userCache.get("user_1_name").orElse("Не найдено"));

        // --- Тест 2: Получение с приведением типа (getAsType) ---
        // Мы говорим: "Достань возраст, и я гарантирую, что это число (Integer.class)"
        Optional<Integer> ageBox = userCache.getAsType("user_1_age", Integer.class);

        // Открываем коробку. Если там правда число, прибавляем к нему 5 лет
        ageBox.ifPresent(age -> System.out.println("Возраст через 5 лет: " + (age + 5)));

        // --- Тест 3: Статистика и очистка ---
        System.out.println("Всего записей в кэше: " + userCache.size());

        userCache.clear(); // Произошел сбой, сбрасываем кэш!

        System.out.println("Записей после очистки: " + userCache.size());
    }
}