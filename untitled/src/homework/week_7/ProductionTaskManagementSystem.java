package homework.week_7;

/*
Комплексная система управления задачами с использованием Queue и Deque
Цель: Продемонстрировать комплексное понимание и применение всех изученных реализаций Queue и
Deque для создания многоуровневой системы управления задачами с различными стратегиями обработки.

Описание: Создайте систему управления задачами производственного процесса, использующую различные
реализации Queue и Deque для обработки задач с разными приоритетами, срочностью и типами обработки.
 */

import java.util.*;
        import java.time.LocalDateTime;

// Перечисление для приоритетов задач
enum TaskPriority {
    URGENT,     // Срочные - немедленная обработка (индекс 0 - наивысший)
    HIGH,       // Высокий приоритет (индекс 1)
    NORMAL,     // Обычный приоритет (индекс 2)
    LOW         // Низкий приоритет (индекс 3)
}

// Перечисление для типов задач
enum TaskType {
    ASSEMBLY, QUALITY_CHECK, MAINTENANCE, PACKAGING, SHIPPING
}

// Перечисление для статусов задач
enum TaskStatus {
    PENDING, IN_PROGRESS, COMPLETED, CANCELLED
}

// Класс для представления производственной задачи
class ProductionTask implements Comparable<ProductionTask> {
    private String taskId;
    private String description;
    private TaskType type;
    private TaskPriority priority;
    private TaskStatus status;
    private int estimatedDuration; // в минутах
    private LocalDateTime createdTime;
    private LocalDateTime startTime;
    private LocalDateTime completedTime;

    public ProductionTask(String taskId, String description, TaskType type,
                          TaskPriority priority, int estimatedDuration) {
        this.taskId = taskId;
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.estimatedDuration = estimatedDuration;
        this.status = TaskStatus.PENDING;
        this.createdTime = LocalDateTime.now();
    }

    // Геттеры
    public String getTaskId() { return taskId; }
    public TaskType getType() { return type; }
    public TaskPriority getPriority() { return priority; }
    public TaskStatus getStatus() { return status; }

    // Сеттеры
    public void setStatus(TaskStatus status) { this.status = status; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    // Реализация для PriorityQueue (сначала приоритет, потом время создания)
    @Override
    public int compareTo(ProductionTask other) {
        int priorityCompare = this.priority.compareTo(other.priority);
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        return this.createdTime.compareTo(other.createdTime);
    }

    // ВАЖНО: equals и hashCode для корректного поиска и удаления по ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductionTask task = (ProductionTask) obj;
        return taskId.equals(task.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }

    @Override
    public String toString() {
        return String.format("Задача #%s: %s [%s, %s, %d мин]",
                taskId, description, type, priority, estimatedDuration);
    }
}

// Основной класс системы управления задачами
public class ProductionTaskManagementSystem {

    private PriorityQueue<ProductionTask> priorityQueue;    // Основная очередь с приоритетами
    private Deque<ProductionTask> urgentDeque;              // Срочные задачи (VIP-пропуск)
    private Queue<ProductionTask> assemblyLine;             // Конвейер сборки (Строго FIFO)
    private Deque<ProductionTask> maintenanceStack;         // Стек техобслуживания (Строго LIFO)

    // Конструктор
    public ProductionTaskManagementSystem() {
        this.priorityQueue = new PriorityQueue<>();
        this.urgentDeque = new ArrayDeque<>();
        this.assemblyLine = new ArrayDeque<>(); // ArrayDeque в роли FIFO
        this.maintenanceStack = new ArrayDeque<>(); // ArrayDeque в роли LIFO
    }

    // --- Задание 1: Добавление задач ---
    public boolean addTask(ProductionTask task) {
        if (task == null) return false;

        // Маршрутизация задачи
        if (task.getPriority() == TaskPriority.URGENT) {
            return urgentDeque.offerFirst(task); // Срочные кидаем в начало!
        } else if (task.getType() == TaskType.ASSEMBLY) {
            return assemblyLine.offer(task); // Сборка идет на конвейер (в конец)
        } else if (task.getType() == TaskType.MAINTENANCE) {
            maintenanceStack.push(task); // Обслуживание идет в стек (наверх)
            return true;
        } else {
            return priorityQueue.offer(task); // Остальные судит PriorityQueue
        }
    }

    public boolean addUrgentTask(ProductionTask task) {
        return urgentDeque.offerFirst(task);
    }

    // --- Задание 2: Обработка задач ---
    public ProductionTask processNextTask() {
        ProductionTask task = null;

        // Диспетчер: строгий порядок обработки
        if (!urgentDeque.isEmpty()) {
            task = urgentDeque.pollFirst();
        } else if (!priorityQueue.isEmpty()) {
            task = priorityQueue.poll();
        } else if (!assemblyLine.isEmpty()) {
            task = assemblyLine.poll();
        } else if (!maintenanceStack.isEmpty()) {
            task = maintenanceStack.pop(); // pop() берет сверху стека
        }

        if (task != null) {
            task.setStatus(TaskStatus.IN_PROGRESS);
            task.setStartTime(LocalDateTime.now());
        }
        return task;
    }

    public ProductionTask processAssemblyTask() {
        ProductionTask task = assemblyLine.poll(); // FIFO
        if (task != null) task.setStatus(TaskStatus.IN_PROGRESS);
        return task;
    }

    public ProductionTask processMaintenanceTask() {
        ProductionTask task = maintenanceStack.pollFirst(); // Эквивалент pop() для LIFO
        if (task != null) task.setStatus(TaskStatus.IN_PROGRESS);
        return task;
    }

    // --- Задание 3: Управление задачами ---
    public boolean escalateTask(String taskId) {
        ProductionTask dummy = new ProductionTask(taskId, "", null, null, 0);
        ProductionTask foundTask = null;

        // Ищем задачу во всех не-срочных очередях и извлекаем её
        if (priorityQueue.remove(dummy)) foundTask = findByIdInIterable(priorityQueue, taskId);
        else if (assemblyLine.remove(dummy)) foundTask = findByIdInIterable(assemblyLine, taskId);
        else if (maintenanceStack.remove(dummy)) foundTask = findByIdInIterable(maintenanceStack, taskId);

        if (foundTask != null) {
            // Если нашли, кидаем в urgent
            return urgentDeque.offerFirst(foundTask);
        }
        return false;
    }

    // Вспомогательный метод для поиска (т.к. remove возвращает boolean, а нам нужен сам объект)
    private ProductionTask findByIdInIterable(Iterable<ProductionTask> iterable, String id) {
        // На практике лучше хранить Map всех задач для O(1) поиска,
        // но здесь работаем чисто с изученными очередями.
        for (ProductionTask t : iterable) {
            if (t.getTaskId().equals(id)) return t;
        }
        return null;
    }

    public boolean cancelTask(String taskId) {
        ProductionTask dummy = new ProductionTask(taskId, "", null, null, 0);
        boolean removed = urgentDeque.remove(dummy) ||
                priorityQueue.remove(dummy) ||
                assemblyLine.remove(dummy) ||
                maintenanceStack.remove(dummy);
        return removed;
    }

    // --- Задание 4: Мониторинг ---
    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("Срочные задачи (Urgent Deque)", urgentDeque.size());
        stats.put("Приоритетные задачи (Priority Queue)", priorityQueue.size());
        stats.put("Конвейер сборки (Assembly Line)", assemblyLine.size());
        stats.put("Стек обслуживания (Maintenance Stack)", maintenanceStack.size());
        stats.put("Всего в ожидании", urgentDeque.size() + priorityQueue.size() +
                assemblyLine.size() + maintenanceStack.size());
        return stats;
    }

    // --- Демонстрация работы системы ---
    public static void main(String[] args) {
        ProductionTaskManagementSystem system = new ProductionTaskManagementSystem();

        system.addTask(new ProductionTask("T001", "Сборка продукта A", TaskType.ASSEMBLY, TaskPriority.NORMAL, 120));
        system.addTask(new ProductionTask("T002", "Срочный ремонт станка", TaskType.MAINTENANCE, TaskPriority.URGENT, 60));
        system.addTask(new ProductionTask("T003", "Контроль качества", TaskType.QUALITY_CHECK, TaskPriority.HIGH, 45));
        system.addTask(new ProductionTask("T004", "Упаковка продукции", TaskType.PACKAGING, TaskPriority.NORMAL, 90));
        system.addTask(new ProductionTask("T005", "Сборка продукта B", TaskType.ASSEMBLY, TaskPriority.LOW, 150));

        System.out.println("=== Статистика после добавления ===");
        system.getSystemStatistics().forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\n=== Глобальная обработка задач (Диспетчер) ===");
        // Ожидаем T002 (URGENT -> urgentDeque)
        System.out.println("Обработана: " + system.processNextTask());
        // Ожидаем T003 (HIGH -> priorityQueue)
        System.out.println("Обработана: " + system.processNextTask());

        System.out.println("\n=== Локальная обработка (Конвейер сборки) ===");
        // Ожидаем T001 (добавлен первым в assemblyLine)
        System.out.println("Сборка: " + system.processAssemblyTask());

        System.out.println("\n=== Эскалация задачи ===");
        System.out.println("Повышаем приоритет T004 до Срочного...");
        system.escalateTask("T004");
        // Теперь T004 должна быть перехвачена главным диспетчером первой
        System.out.println("Следующая задача в системе: " + system.processNextTask());
    }
}
