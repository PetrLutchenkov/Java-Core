package homework.week_7;

/*
Система обработки заявок с использованием Queue и Deque
Цель: Освоить практическое применение реализаций Queue и Deque (ArrayDeque,
PriorityQueue) для решения задач обработки данных в порядке FIFO и с приоритетами.

Описание: Создайте систему обработки заявок, где различные типы заявок требуют разных стратегий
обработки с использованием ArrayDeque для обычных заявок и PriorityQueue для приоритетных.
 */

import java.util.*;
import java.time.LocalDateTime;

// Перечисление для приоритетов заявок
enum Priority {
    CRITICAL,    // Критический - обрабатывается первым
    HIGH,        // Высокий
    NORMAL,      // Обычный
    LOW          // Низкий
}

// Перечисление для типов заявок
enum RequestType {
    TECHNICAL_SUPPORT,    // Техническая поддержка
    BILLING,              // Биллинг
    FEATURE_REQUEST,      // Запрос функционала
    BUG_REPORT            // Сообщение об ошибке
}

// Класс для представления заявки
class SupportRequest implements Comparable<SupportRequest> {
    private String id;
    private String customerName;
    private RequestType type;
    private Priority priority;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime processedTime;

    public SupportRequest(String id, String customerName, RequestType type,
                          Priority priority, String description) {
        this.id = id;
        this.customerName = customerName;
        this.type = type;
        this.priority = priority;
        this.description = description;
        this.createdTime = LocalDateTime.now();
    }

    // Геттеры
    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public RequestType getType() { return type; }
    public Priority getPriority() { return priority; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public LocalDateTime getProcessedTime() { return processedTime; }

    public void setProcessedTime(LocalDateTime processedTime) {
        this.processedTime = processedTime;
    }

    // ВАЖНО: Реализация для PriorityQueue 
    // Enum в Java уже реализует Comparable (по порядку объявления).
    // CRITICAL (0) < HIGH (1) < NORMAL (2) < LOW (3). 
    // PriorityQueue всегда достает "наименьший" элемент, поэтому CRITICAL выйдет первым!
    @Override
    public int compareTo(SupportRequest other) {
        int priorityComparison = this.priority.compareTo(other.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        // Если приоритеты равны, сравниваем по времени создания (кто раньше пришел)
        return this.createdTime.compareTo(other.createdTime);
    }

    // Для удобного удаления по ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SupportRequest request = (SupportRequest) obj;
        return id.equals(request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Заявка #%s: %s [%s] - %s",
                id, customerName, priority, description);
    }
}

// Основной класс системы обработки заявок
public class RequestProcessingSystem {
    // Используем интерфейсы (пульты) для объявления переменных
    private Queue<SupportRequest> normalQueue;          // Обычная очередь (FIFO)
    private PriorityQueue<SupportRequest> priorityQueue; // Приоритетная очередь
    private Deque<SupportRequest> urgentDeque;          // Срочные заявки (двусторонняя)

    // Конструктор
    public RequestProcessingSystem() {
        // Подключаем конкретные "телевизоры"
        this.normalQueue = new ArrayDeque<>();
        this.priorityQueue = new PriorityQueue<>();
        this.urgentDeque = new ArrayDeque<>();
    }

    // --- Задание 1: Добавление заявок ---
    public boolean addRequest(SupportRequest request) {
        if (request == null) return false;

        switch (request.getPriority()) {
            case CRITICAL:
                // Критические кидаем в начало двусторонней очереди!
                return urgentDeque.offerFirst(request);
            case HIGH:
                // Высокий приоритет отдаем судье PriorityQueue
                return priorityQueue.offer(request);
            case NORMAL:
            case LOW:
                // Обычные и низкие встают в обычную очередь в конец
                return normalQueue.offer(request);
            default:
                return false;
        }
    }

    public boolean addUrgentRequest(SupportRequest request) {
        if (request == null) return false;
        // Принудительно ставим в самый перед (VIP)
        return urgentDeque.offerFirst(request);
    }

    // --- Задание 2: Обработка заявок ---
    public SupportRequest processNextRequest() {
        SupportRequest requestToProcess = null;

        // Строгая иерархия: Сначала спасаем тех, кто в urgentDeque
        if (!urgentDeque.isEmpty()) {
            requestToProcess = urgentDeque.pollFirst();
        }
        // Затем тех, кто в PriorityQueue
        else if (!priorityQueue.isEmpty()) {
            requestToProcess = priorityQueue.poll();
        }
        // И только потом обычную очередь
        else if (!normalQueue.isEmpty()) {
            requestToProcess = normalQueue.poll();
        }

        // Если нашли кого обработать - ставим штамп времени
        if (requestToProcess != null) {
            requestToProcess.setProcessedTime(LocalDateTime.now());
        }

        return requestToProcess;
    }

    public SupportRequest processLastRequest() {
        // Если кто-то отменил последнюю срочную операцию (LIFO поведение)
        if (!urgentDeque.isEmpty()) {
            SupportRequest request = urgentDeque.pollLast();
            request.setProcessedTime(LocalDateTime.now());
            return request;
        }
        return null;
    }

    // --- Задание 3: Просмотр заявок ---
    public SupportRequest peekNextRequest() {
        // Логика та же, только используем peek() вместо poll()
        if (!urgentDeque.isEmpty()) return urgentDeque.peekFirst();
        if (!priorityQueue.isEmpty()) return priorityQueue.peek();
        if (!normalQueue.isEmpty()) return normalQueue.peek();
        return null;
    }

    public void displayAllQueues() {
        System.out.println("--- СОСТОЯНИЕ ОЧЕРЕДЕЙ ---");
        System.out.println("1. Критические (Urgent Deque): " + urgentDeque.size() + " шт.");
        for(SupportRequest r : urgentDeque) System.out.println("   " + r);

        System.out.println("2. Высокий приоритет (Priority Queue): " + priorityQueue.size() + " шт.");
        // Помни про "полу-хаос" PriorityQueue при итерации! Но для вывода пойдет.
        for(SupportRequest r : priorityQueue) System.out.println("   " + r);

        System.out.println("3. Обычные (Normal Queue): " + normalQueue.size() + " шт.");
        for(SupportRequest r : normalQueue) System.out.println("   " + r);
    }

    // --- Задание 4: Статистика и анализ ---
    public String getQueueStatistics() {
        int total = urgentDeque.size() + priorityQueue.size() + normalQueue.size();
        return String.format("Всего заявок в ожидании: %d\n" +
                        "- Критических: %d\n" +
                        "- Приоритетных: %d\n" +
                        "- Обычных: %d",
                total, urgentDeque.size(), priorityQueue.size(), normalQueue.size());
    }

    public List<SupportRequest> getRequestsByType(RequestType type) {
        List<SupportRequest> result = new ArrayList<>();
        // Собираем со всех трех очередей
        for (SupportRequest r : urgentDeque) if (r.getType() == type) result.add(r);
        for (SupportRequest r : priorityQueue) if (r.getType() == type) result.add(r);
        for (SupportRequest r : normalQueue) if (r.getType() == type) result.add(r);
        return result;
    }

    // --- Задание 5: Управление очередями ---
    public boolean promoteRequest(String requestId) {
        // Ищем в обычной очереди
        for (SupportRequest request : normalQueue) {
            if (request.getId().equals(requestId)) {
                normalQueue.remove(request); // Убираем из обычной
                // Повышаем приоритет до HIGH и кидаем в PriorityQueue
                SupportRequest promoted = new SupportRequest(
                        request.getId(), request.getCustomerName(), request.getType(),
                        Priority.HIGH, request.getDescription() + " (ПОВЫШЕН ПРИОРИТЕТ)"
                );
                return priorityQueue.offer(promoted);
            }
        }
        return false;
    }

    public boolean removeRequest(String requestId) {
        // Создаем "пустышку" для сравнения по ID (спасибо переопределенному equals)
        SupportRequest dummy = new SupportRequest(requestId, "", null, null, "");
        boolean removed = urgentDeque.remove(dummy);
        removed |= priorityQueue.remove(dummy);
        removed |= normalQueue.remove(dummy);
        return removed;
    }

    public void clearAllQueues() {
        urgentDeque.clear();
        priorityQueue.clear();
        normalQueue.clear();
    }

    public boolean hasCriticalRequests() {
        return !urgentDeque.isEmpty();
    }

    // --- Демонстрация работы системы ---
    public static void main(String[] args) {
        RequestProcessingSystem system = new RequestProcessingSystem();

        System.out.println("--- 1. Добавление заявок ---");
        system.addRequest(new SupportRequest("001", "Иван Иванов", RequestType.TECHNICAL_SUPPORT, Priority.NORMAL, "Не работает принтер"));
        system.addRequest(new SupportRequest("002", "Петр Петров", RequestType.BILLING, Priority.HIGH, "Ошибка в счете"));
        system.addRequest(new SupportRequest("003", "Мария Сидорова", RequestType.BUG_REPORT, Priority.CRITICAL, "Система не доступна"));
        system.addRequest(new SupportRequest("004", "Анна Козлова", RequestType.FEATURE_REQUEST, Priority.LOW, "Добавить кнопку"));

        // Вкидываем VIP заявку вне очереди
        system.addUrgentRequest(new SupportRequest("VIP-1", "Директор", RequestType.TECHNICAL_SUPPORT, Priority.CRITICAL, "Забыл пароль!"));

        system.displayAllQueues();

        System.out.println("\n--- 2. Обработка заявок (Строгий порядок) ---");
        // Ожидаем: VIP-1 (т.к. добавили через urgent offerFirst), затем 003, затем 002, затем 001, затем 004
        System.out.println("Берем в работу: " + system.processNextRequest().getId());
        System.out.println("Берем в работу: " + system.processNextRequest().getId());
        System.out.println("Берем в работу: " + system.processNextRequest().getId());

        System.out.println("\n--- 3. Статистика после обработки ---");
        System.out.println(system.getQueueStatistics());

        System.out.println("\n--- 4. Повышение приоритета (Эскалация) ---");
        System.out.println("Кто следующий в очереди до повышения: " + system.peekNextRequest().getId());
        // Повышаем приоритет заявки 004 (которая была LOW)
        system.promoteRequest("004");
        System.out.println("Кто следующий в очереди после повышения: " + system.peekNextRequest().getId() + " (Это наша заявка 004!)");
    }
}