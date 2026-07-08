package homework.week_8;

import java.util.*;

public class AccessControlSystem {

    // --- Задача 1: Управление пользователями и ролями ---

    enum Role {
        ADMIN, EDITOR, VIEWER, GUEST, MODERATOR
    }

    static class User {
        private final String username;
        // Обычный HashSet: максимально быстрый O(1), порядок не важен
        private final Set<Role> roles = new HashSet<>();

        public User(String username) {
            this.username = username;
        }

        public String getUsername() { return username; }

        // Возвращаем неизменяемую копию (Set.copyOf), чтобы никто не сломал права извне
        public Set<Role> getRoles() { return Set.copyOf(roles); }

        public void addRole(Role role) { roles.add(role); }
        public void removeRole(Role role) { roles.remove(role); }
        public boolean hasRole(Role role) { return roles.contains(role); }

        // Бизнес-ключ - имя пользователя. Если имена равны, это один и тот же человек.
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            User user = (User) obj;
            return Objects.equals(username, user.username);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username);
        }

        @Override
        public String toString() {
            return username + " " + roles;
        }
    }

    // --- Задача 2: Управление правами доступа ---

    static class Resource {
        private final String name;
        // EnumSet: хранится как одно число (long), проверка прав выполняется битовыми операциями
        private final EnumSet<Role> allowedRoles;

        public Resource(String name, Role... roles) {
            this.name = name;
            // Если роли не переданы, создаем пустое множество.
            // Иначе создаем EnumSet из переданного массива.
            if (roles.length > 0) {
                this.allowedRoles = EnumSet.copyOf(Arrays.asList(roles));
            } else {
                this.allowedRoles = EnumSet.noneOf(Role.class);
            }
        }

        public boolean canAccess(User user) {
            // Элегантная проверка: Collections.disjoint возвращает true,
            // если у двух множеств НЕТ общих элементов.
            // Значит, если они НЕ disjoint (!), доступ разрешен!
            return !Collections.disjoint(allowedRoles, user.getRoles());
        }

        public String getName() { return name; }
    }

    // --- Задача 3: Система аудита с LRU-кэшем ---

    static class AuditLog {
        // LinkedHashSet запоминает порядок вставки.
        private final LinkedHashSet<String> recentActions;
        private final int maxSize;

        public AuditLog(int maxSize) {
            this.maxSize = maxSize;
            this.recentActions = new LinkedHashSet<>(maxSize);
        }

        public void logAction(String action) {
            // Чтобы обновить элемент и сделать его "самым новым",
            // мы должны сначала удалить его, а затем добавить в конец очереди.
            recentActions.remove(action);
            recentActions.add(action);

            // Если лимит превышен, удаляем самый старый (он всегда первый в итераторе)
            if (recentActions.size() > maxSize) {
                Iterator<String> iterator = recentActions.iterator();
                iterator.next();    // Берем первый элемент
                iterator.remove();  // И выкидываем его
            }
        }

        public Set<String> getRecentActions() {
            return Set.copyOf(recentActions); // Безопасная копия
        }
    }

    // --- Задача 4: Отчеты и статистика ---

    static class SystemReport {
        // TreeSet автоматически сортирует юзеров. Передаем внешний Comparator (по алфавиту)
        private final TreeSet<User> usersByActivity;

        public SystemReport() {
            this.usersByActivity = new TreeSet<>(
                    Comparator.comparing(User::getUsername)
            );
        }

        public void addUser(User user) {
            usersByActivity.add(user);
        }

        public Set<User> getUsersAlphabetically() {
            return Set.copyOf(usersByActivity);
        }

        public Set<User> getUsersWithRole(Role role) {
            // Используем LinkedHashSet, чтобы сохранить красивую алфавитную сортировку
            // от исходного TreeSet при фильтрации!
            Set<User> result = new LinkedHashSet<>();
            for (User user : usersByActivity) {
                if (user.hasRole(role)) {
                    result.add(user);
                }
            }
            return result;
        }

        public Map<Role, Integer> getRoleStatistics() {
            // Забегая вперед к теме Map: EnumMap - это ультрабыстрый аналог EnumSet для словарей
            Map<Role, Integer> stats = new EnumMap<>(Role.class);
            for (Role role : Role.values()) {
                stats.put(role, 0); // Инициализация нулями
            }

            for (User user : usersByActivity) {
                for (Role role : user.getRoles()) {
                    stats.put(role, stats.get(role) + 1);
                }
            }
            return stats;
        }
    }

    // --- Демонстрация работы системы ---

    public static void main(String[] args) {
        System.out.println("=== 1. Проверка доступа (HashSet + EnumSet) ===");
        User admin = new User("admin");
        admin.addRole(Role.ADMIN);

        User guest = new User("guest");
        guest.addRole(Role.GUEST);

        Resource secretFile = new Resource("secret.txt", Role.ADMIN, Role.EDITOR);

        System.out.println("Admin может читать secret.txt: " + secretFile.canAccess(admin)); // true
        System.out.println("Guest может читать secret.txt: " + secretFile.canAccess(guest)); // false

        System.out.println("\n=== 2. Система Аудита LRU (LinkedHashSet) ===");
        AuditLog log = new AuditLog(3); // Храним только 3 последних действия
        log.logAction("Login Admin");
        log.logAction("Read File");
        log.logAction("Download File");
        System.out.println("Лог (3 действия): " + log.getRecentActions());

        // Лимит превышен, "Login Admin" должно исчезнуть
        log.logAction("Logout Admin");
        System.out.println("Лог после переполнения: " + log.getRecentActions());

        // Обновляем старое действие ("Read File"). Оно должно прыгнуть в конец списка!
        log.logAction("Read File");
        System.out.println("Лог после обновления: " + log.getRecentActions());

        System.out.println("\n=== 3. Отчеты и статистика (TreeSet) ===");
        SystemReport report = new SystemReport();
        report.addUser(new User("zebra_user"));
        report.addUser(admin);
        report.addUser(new User("apple_user"));
        report.addUser(guest);

        System.out.println("Пользователи по алфавиту:");
        report.getUsersAlphabetically().forEach(System.out::println);

        System.out.println("\nСтатистика распределения ролей:");
        report.getRoleStatistics().forEach((role, count) ->
                System.out.println(role + ": " + count)
        );
    }
}