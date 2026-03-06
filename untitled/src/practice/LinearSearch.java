package practice;

public class LinearSearch {
    public static void main(String[] args) {
        String[] names = {"Анна", "Борис", "Виктор", "Галина", "Дмитрий", "Анна"};
        String target = "Анна";

        System.out.println("Поиск всех вхождений '" + target + "':");

        boolean found = false;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(target)) {
                System.out.println("Найдено на позиции: " + i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Элемент не найден");
        }
    }
}
