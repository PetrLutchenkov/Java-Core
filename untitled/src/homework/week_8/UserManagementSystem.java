package homework.week_8;

import java.util.*;

/*
СИСТЕМА УПРАВЛЕНИЯ УНИКАЛЬНЫМИ ДАННЫМИ С HASHSET
 */

// Класс для представления пользователя
class User {
    private String username;
    private String email;
    private int age;

    public User(String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public int getAge() { return age; }

    // ЗОЛОТОЕ ПРАВИЛО: Бизнес-ключом считаем email и username.
    // Если они совпадают - это один и тот же человек.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(username, user.username) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    @Override
    public String toString() {
        return String.format("Пользователь: %s (%s), возраст: %d", username, email, age);
    }
}

// Класс для представления группы пользователей
class UserGroup {
    private String groupName;
    private HashSet<User> members;

    public UserGroup(String groupName) {
        this.groupName = groupName;
        this.members = new HashSet<>();
    }

    public String getGroupName() { return groupName; }
    public HashSet<User> getMembers() { return members; }

    // Группы уникальны по своему названию
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserGroup group = (UserGroup) obj;
        return Objects.equals(groupName, group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName);
    }

    @Override
    public String toString() {
        return String.format("Группа: %s, участников: %d", groupName, members.size());
    }
}

// Основной класс системы управления
public class UserManagementSystem {
    private HashSet<User> allUsers;
    private HashSet<UserGroup> allGroups;

    public UserManagementSystem() {
        this.allUsers = new HashSet<>();
        this.allGroups = new HashSet<>();
    }

    // --- Задание 1: Базовые операции ---

    public boolean addUser(User user) {
        // Метод add() сам вернет false, если дубликат уже есть в системе
        return allUsers.add(user);
    }

    public boolean removeUser(User user) {
        boolean removed = allUsers.remove(user);
        if (removed) {
            // Если удалили из системы, надо вычистить его из всех групп
            for (UserGroup group : allGroups) {
                group.getMembers().remove(user);
            }
        }
        return removed;
    }

    public boolean containsUser(User user) {
        // Мгновенная проверка за O(1)
        return allUsers.contains(user);
    }

    public User findUserByUsername(String username) {
        // В Set нет метода get(), поэтому ищем перебором
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // --- Задание 2: Операции с группами ---

    public boolean createGroup(String groupName) {
        return allGroups.add(new UserGroup(groupName));
    }

    private UserGroup findGroupByName(String groupName) {
        for (UserGroup group : allGroups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    public boolean addUserToGroup(User user, String groupName) {
        if (!allUsers.contains(user)) return false; // Защита от несуществующих юзеров
        UserGroup group = findGroupByName(groupName);
        if (group != null) {
            return group.getMembers().add(user);
        }
        return false;
    }

    public boolean removeUserFromGroup(User user, String groupName) {
        UserGroup group = findGroupByName(groupName);
        if (group != null) {
            return group.getMembers().remove(user);
        }
        return false;
    }

    // --- Задание 3: Операции с множествами (Магия) ---

    public HashSet<User> getUsersInMultipleGroups() {
        HashSet<User> seenOnce = new HashSet<>();
        HashSet<User> inMultiple = new HashSet<>();

        for (UserGroup group : allGroups) {
            for (User user : group.getMembers()) {
                // Если add вернул false, значит он уже был в другой группе!
                if (!seenOnce.add(user)) {
                    inMultiple.add(user);
                }
            }
        }
        return inMultiple;
    }

    public HashSet<User> getUsersWithoutGroups() {
        // Копируем всех юзеров
        HashSet<User> lonelyUsers = new HashSet<>(allUsers);

        // Вычитаем (removeAll) из них тех, кто есть хоть в одной группе
        for (UserGroup group : allGroups) {
            lonelyUsers.removeAll(group.getMembers());
        }
        return lonelyUsers;
    }

    public HashSet<User> getCommonUsers(String group1Name, String group2Name) {
        UserGroup g1 = findGroupByName(group1Name);
        UserGroup g2 = findGroupByName(group2Name);

        if (g1 == null || g2 == null) return new HashSet<>();

        // Делаем копию первой группы
        HashSet<User> common = new HashSet<>(g1.getMembers());
        // Оставляем ТОЛЬКО тех, кто есть во второй группе (Пересечение множеств)
        common.retainAll(g2.getMembers());
        return common;
    }

    // --- Задание 4: Статистика и анализ ---

    public String getSystemStatistics() {
        int totalAge = 0;
        for (User u : allUsers) {
            totalAge += u.getAge();
        }
        double avgAge = allUsers.isEmpty() ? 0 : (double) totalAge / allUsers.size();

        return String.format("Статистика: Юзеров: %d, Групп: %d, Средний возраст: %.1f",
                allUsers.size(), allGroups.size(), avgAge);
    }

    public HashSet<UserGroup> getMostPopularGroups(int count) {
        // Конвертируем в List для сортировки
        List<UserGroup> sortedGroups = new ArrayList<>(allGroups);
        sortedGroups.sort((g1, g2) -> Integer.compare(g2.getMembers().size(), g1.getMembers().size()));

        HashSet<UserGroup> popular = new LinkedHashSet<>(); // Сохраняем порядок
        for (int i = 0; i < Math.min(count, sortedGroups.size()); i++) {
            popular.add(sortedGroups.get(i));
        }
        return popular;
    }

    public HashSet<User> getUsersByAgeRange(int minAge, int maxAge) {
        HashSet<User> filtered = new HashSet<>();
        for (User user : allUsers) {
            if (user.getAge() >= minAge && user.getAge() <= maxAge) {
                filtered.add(user);
            }
        }
        return filtered;
    }

    // --- Задание 5: Импорт/Экспорт ---

    public int importUsers(HashSet<User> usersToImport) {
        int addedCount = 0;
        for (User user : usersToImport) {
            if (allUsers.add(user)) {
                addedCount++;
            }
        }
        return addedCount;
    }

    public HashSet<User> exportGroupUsers(String groupName) {
        UserGroup group = findGroupByName(groupName);
        return group != null ? new HashSet<>(group.getMembers()) : new HashSet<>();
    }

    public boolean mergeGroups(String group1Name, String group2Name, String newGroupName) {
        UserGroup g1 = findGroupByName(group1Name);
        UserGroup g2 = findGroupByName(group2Name);

        if (g1 == null || g2 == null) return false;

        createGroup(newGroupName);
        UserGroup newGroup = findGroupByName(newGroupName);

        // Объединение множеств (addAll) - дубликаты уберутся автоматически!
        newGroup.getMembers().addAll(g1.getMembers());
        newGroup.getMembers().addAll(g2.getMembers());

        return true;
    }

    // --- Задание 6: Валидация ---

    public boolean validateSystemIntegrity() {
        for (UserGroup group : allGroups) {
            // Если в группе есть юзер, которого нет в общей базе - система сломана
            if (!allUsers.containsAll(group.getMembers())) {
                return false;
            }
        }
        return true;
    }

    public HashSet<User> findDuplicateUsers() {
        // Проверяем логические дубликаты (например, разные объекты с одинаковыми email)
        HashSet<String> seenEmails = new HashSet<>();
        HashSet<User> duplicates = new HashSet<>();

        for (User user : allUsers) {
            if (!seenEmails.add(user.getEmail())) {
                duplicates.add(user);
            }
        }
        return duplicates;
    }

    public int cleanupInvalidData() {
        int removedCount = 0;
        // Используем итератор для безопасного удаления элементов во время цикла
        Iterator<User> iterator = allUsers.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername() == null || user.getEmail() == null || !user.getEmail().contains("@")) {
                iterator.remove();
                removedCount++;

                // Чистим и в группах
                for (UserGroup group : allGroups) {
                    group.getMembers().remove(user);
                }
            }
        }
        return removedCount;
    }

    // --- Демонстрация работы ---

    public static void main(String[] args) {
        UserManagementSystem system = new UserManagementSystem();

        // 1. Добавление
        User user1 = new User("alice", "alice@example.com", 25);
        User user2 = new User("bob", "bob@example.com", 30);
        User user3 = new User("charlie", "charlie@example.com", 35);

        system.addUser(user1);
        system.addUser(user2);
        system.addUser(user3);

        // Попытка добавить дубликат
        boolean isAdded = system.addUser(new User("alice", "alice@example.com", 25));
        System.out.println("Дубликат добавлен? " + isAdded); // false

        // 2. Группы
        system.createGroup("developers");
        system.createGroup("managers");

        system.addUserToGroup(user1, "developers");
        system.addUserToGroup(user2, "developers");
        system.addUserToGroup(user2, "managers");
        system.addUserToGroup(user3, "managers");

        // 3. Магия множеств
        System.out.println("\n--- Операции над множествами ---");
        HashSet<User> commonUsers = system.getCommonUsers("developers", "managers");
        System.out.println("Общие пользователи (Боб): " + commonUsers);

        HashSet<User> multipleGroups = system.getUsersInMultipleGroups();
        System.out.println("Состоят в нескольких группах (Боб): " + multipleGroups);

        // 4. Валидация
        System.out.println("\nЦелостность системы: " + system.validateSystemIntegrity());

        // 5. Очистка инвалидных данных
        system.addUser(new User("hacker", "bademail", 99)); // Нет собаки в email
        System.out.println("Удалено кривых данных: " + system.cleanupInvalidData());

        // 6. Статистика
        System.out.println("\n" + system.getSystemStatistics());
    }
}