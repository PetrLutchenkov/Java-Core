package homework.week_8;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
СИСТЕМА УПРАВЛЕНИЯ СОБЫТИЯМИ С РАЗЛИЧНЫМИ SET РЕАЛИЗАЦИЯМИ
 */
enum EventType {
    INFO, WARNING, ERROR, SECURITY, PERFORMANCE
}

class Event implements Comparable<Event> {
    private String id;
    private EventType type;
    private String message;
    private LocalDateTime timestamp;
    private int priority; // 1-высокий, 5-низкий

    public Event(String id, EventType type, String message, int priority) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.priority = priority;
    }

    // Геттеры
    public String getId() { return id; }
    public EventType getType() { return type; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getPriority() { return priority; }

    // Сравнение: сначала по приоритету (1 — выше), затем по времени (новее — позже)
    @Override
    public int compareTo(Event other) {
        if (this.priority != other.priority) {
            return Integer.compare(this.priority, other.priority); // 1 < 2 → выше приоритет
        }
        return this.timestamp.compareTo(other.timestamp); // если приоритет равен — по времени
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Event)) return false;
        Event other = (Event) obj;
        return this.id.equals(other.id); // события равны, если совпадает id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // хэш зависит только от id
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return String.format("[%s] %s: %s (приоритет: %d, время: %s)",
                id, type, message, priority, timestamp.format(formatter));
    }
}

public class EventManagementSystem {
    private Set<Event> allEvents;           // HashSet — быстро, порядок не важен
    private Set<Event> orderedEvents;       // LinkedHashSet — порядок добавления
    private Set<Event> prioritizedEvents;   // TreeSet — сортировка по приоритету

    public EventManagementSystem() {
        this.allEvents = new HashSet<>();
        this.orderedEvents = new LinkedHashSet<>();
        this.prioritizedEvents = new TreeSet<>();
    }

    // === Задание 1: Добавление событий ===
    public boolean addEvent(Event event) {
        if (allEvents.contains(event)) {
            return false; // дубликат по id
        }
        allEvents.add(event);
        orderedEvents.add(event);
        prioritizedEvents.add(event);
        return true;
    }

    public int addEvents(Collection<Event> events) {
        int added = 0;
        for (Event event : events) {
            if (addEvent(event)) {
                added++;
            }
        }
        return added;
    }

    // === Задание 2: Получение событий в разных порядках ===
    public Set<Event> getAllEvents() {
        return new HashSet<>(allEvents); // копия, чтобы не модифицировать оригинал
    }

    public List<Event> getEventsInOrder() {
        return new ArrayList<>(orderedEvents); // LinkedHashSet сохраняет порядок
    }

    public List<Event> getEventsByPriority() {
        return new ArrayList<>(prioritizedEvents); // TreeSet уже отсортирован
    }

    // === Задание 3: Фильтрация и поиск ===
    public Set<Event> getEventsByType(EventType type) {
        Set<Event> result = new HashSet<>();
        for (Event e : allEvents) {
            if (e.getType() == type) {
                result.add(e);
            }
        }
        return result;
    }

    public Set<Event> getHighPriorityEvents() {
        // Приоритет 1 или 2
        Set<Event> result = new TreeSet<>(); // для автоматической сортировки
        for (Event e : prioritizedEvents) {
            if (e.getPriority() <= 2) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Event> getRecentEvents(int count) {
        List<Event> list = new ArrayList<>(orderedEvents);
        int size = list.size();
        int fromIndex = Math.max(0, size - count);
        return list.subList(fromIndex, size);
    }

    // === Задание 4: Временные диапазоны ===
    public Set<Event> getEventsFromLastHour() {
        LocalDateTime hourAgo = LocalDateTime.now().minusHours(1);
        Set<Event> result = new HashSet<>();
        for (Event e : allEvents) {
            if (e.getTimestamp().isAfter(hourAgo)) {
                result.add(e);
            }
        }
        return result;
    }

    public Set<Event> getEventsInTimeRange(LocalDateTime start, LocalDateTime end) {
        Set<Event> result = new HashSet<>();
        for (Event e : allEvents) {
            LocalDateTime ts = e.getTimestamp();
            if (!ts.isBefore(start) && !ts.isAfter(end)) {
                result.add(e);
            }
        }
        return result;
    }

    // === Задание 5: Статистика ===
    public String getEventStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Статистика событий ===\n");
        sb.append("Всего событий: ").append(allEvents.size()).append("\n");

        // По типам
        Map<EventType, Integer> typeCount = new EnumMap<>(EventType.class);
        for (EventType type : EventType.values()) {
            typeCount.put(type, 0);
        }
        for (Event e : allEvents) {
            typeCount.put(e.getType(), typeCount.get(e.getType()) + 1);
        }
        sb.append("По типам: ").append(typeCount).append("\n");

        // По приоритетам
        Map<Integer, Integer> priorityCount = new TreeMap<>();
        for (int i = 1; i <= 5; i++) {
            priorityCount.put(i, 0);
        }
        for (Event e : allEvents) {
            priorityCount.put(e.getPriority(), priorityCount.get(e.getPriority()) + 1);
        }
        sb.append("По приоритетам: ").append(priorityCount).append("\n");

        return sb.toString();
    }

    public EventType getMostFrequentEventType() {
        Map<EventType, Integer> count = new EnumMap<>(EventType.class);
        for (Event e : allEvents) {
            count.put(e.getType(), count.getOrDefault(e.getType(), 0) + 1);
        }
        return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Map<Integer, Integer> getPriorityDistribution() {
        Map<Integer, Integer> result = new TreeMap<>();
        for (int i = 1; i <= 5; i++) {
            result.put(i, 0);
        }
        for (Event e : allEvents) {
            result.put(e.getPriority(), result.get(e.getPriority()) + 1);
        }
        return result;
    }

    // === Задание 6: Очистка ===
    public int removeOldEvents(LocalDateTime threshold) {
        List<Event> toRemove = new ArrayList<>();
        for (Event e : allEvents) {
            if (e.getTimestamp().isBefore(threshold)) {
                toRemove.add(e);
            }
        }
        int count = toRemove.size();
        for (Event e : toRemove) {
            allEvents.remove(e);
            orderedEvents.remove(e);
            prioritizedEvents.remove(e);
        }
        return count;
    }

    public int removeEventsByType(EventType type) {
        List<Event> toRemove = new ArrayList<>();
        for (Event e : allEvents) {
            if (e.getType() == type) {
                toRemove.add(e);
            }
        }
        int count = toRemove.size();
        for (Event e : toRemove) {
            allEvents.remove(e);
            orderedEvents.remove(e);
            prioritizedEvents.remove(e);
        }
        return count;
    }

    public void clearAllEvents() {
        allEvents.clear();
        orderedEvents.clear();
        prioritizedEvents.clear();
    }

    // === Задание 7: Сравнение производительности ===
    public void comparePerformance() {
        System.out.println("\n=== Сравнение производительности ===");

        int N = 10_000;
        List<Event> testData = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            testData.add(new Event("T" + i, EventType.INFO, "Тестовое событие " + i, 3));
        }

        // HashSet
        Set<Event> hashSet = new HashSet<>();
        long start = System.nanoTime();
        for (Event e : testData) hashSet.add(e);
        long hashAdd = System.nanoTime() - start;

        start = System.nanoTime();
        hashSet.contains(testData.get(N/2));
        long hashFind = System.nanoTime() - start;

        // LinkedHashSet
        Set<Event> linkedSet = new LinkedHashSet<>();
        start = System.nanoTime();
        for (Event e : testData) linkedSet.add(e);
        long linkedAdd = System.nanoTime() - start;

        start = System.nanoTime();
        linkedSet.contains(testData.get(N/2));
        long linkedFind = System.nanoTime() - start;

        // TreeSet
        Set<Event> treeSet = new TreeSet<>();
        start = System.nanoTime();
        for (Event e : testData) treeSet.add(e);
        long treeAdd = System.nanoTime() - start;

        start = System.nanoTime();
        treeSet.contains(testData.get(N/2));
        long treeFind = System.nanoTime() - start;

        System.out.printf("Добавление %d событий:\n", N);
        System.out.printf("  HashSet:       %,d нс\n", hashAdd);
        System.out.printf("  LinkedHashSet: %,d нс\n", linkedAdd);
        System.out.printf("  TreeSet:       %,d нс\n", treeAdd);

        System.out.printf("Поиск одного события:\n");
        System.out.printf("  HashSet:       %,d нс\n", hashFind);
        System.out.printf("  LinkedHashSet: %,d нс\n", linkedFind);
        System.out.printf("  TreeSet:       %,d нс\n", treeFind);
    }

    // === Задание 8: Специализированные выборки ===
    public Set<EventType> getEventTypesInOrder() {
        Set<EventType> result = new LinkedHashSet<>();
        for (Event e : orderedEvents) {
            result.add(e.getType());
        }
        return result;
    }

    public Set<Event> getEventsSortedByType() {
        // TreeSet с компаратором по типу
        Set<Event> sorted = new TreeSet<>(Comparator.comparing(Event::getType));
        sorted.addAll(allEvents);
        return sorted;
    }

    public Set<Event> findDuplicateEvents() {
        Set<Event> result = new HashSet<>();
        Set<String> seen = new HashSet<>();

        for (Event e : allEvents) {
            String key = e.getType() + "|" + e.getMessage();
            if (!seen.add(key)) {
                result.add(e);
            }
        }
        return result;
    }

    // === Демонстрация ===
    public static void main(String[] args) {
        EventManagementSystem system = new EventManagementSystem();

        system.addEvent(new Event("001", EventType.INFO, "Система запущена", 3));
        system.addEvent(new Event("002", EventType.WARNING, "Высокая загрузка CPU", 2));
        system.addEvent(new Event("003", EventType.ERROR, "Ошибка базы данных", 1));
        system.addEvent(new Event("004", EventType.SECURITY, "Попытка несанкционированного доступа", 1));
        system.addEvent(new Event("005", EventType.INFO, "Резервное копирование завершено", 4));

        System.out.println("=== Все события ===");
        system.getAllEvents().forEach(System.out::println);

        System.out.println("\n=== В порядке добавления ===");
        system.getEventsInOrder().forEach(System.out::println);

        System.out.println("\n=== По приоритету ===");
        system.getEventsByPriority().forEach(System.out::println);

        System.out.println("\n=== События ERROR ===");
        system.getEventsByType(EventType.ERROR).forEach(System.out::println);

        System.out.println("\n=== Высокий приоритет ===");
        system.getHighPriorityEvents().forEach(System.out::println);

        System.out.println("\n=== Последние 3 события ===");
        system.getRecentEvents(3).forEach(System.out::println);

        System.out.println("\n=== Статистика ===");
        System.out.println(system.getEventStatistics());

        System.out.println("\n=== Самый частый тип: " + system.getMostFrequentEventType());

        System.out.println("\n=== Уникальные типы по порядку: " + system.getEventTypesInOrder());

        System.out.println("\n=== События, отсортированные по типу:");
        system.getEventsSortedByType().forEach(System.out::println);

        system.comparePerformance();
    }
}
