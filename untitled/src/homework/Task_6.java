package homework;

public class Task_6 {

    /*
    Вам снова нужно будет проанализировать успеваемость студентов
    как мы это уже делали, но теперь по нескольким предметам за семестр.
    Создайте двумерный массив grades для хранения оценок 4 студентов по 3 предметам.
    Заполните массив произвольными оценками от 2 до 5.
        Пример структуры
    Студент 1: Математика, Физика, Программирование
    Студент 2: Математика, Физика, Программирование
    Студент 3: Математика, Физика, Программирование
    Студент 4: Математика, Физика, Программирование
        Напишите программу, которая:
    1.Выводит таблицу успеваемости в читаемом виде
    2.Для каждого студента вычисляет средний балл по всем предметам
    3.Для каждого предмета вычисляет средний балл всех студентов\
    4.Находит студента с наивысшим средним баллом
        Усложнение (по желанию):
    Определите предмет с наивысшим средним баллом
    Подсчитайте количество отличников (студентов со средним баллом ≥ 4.5)
     */

    public static void main(String[] args) {

        String[] subjects = {"Математика", "Физика", "Программирование"};
        int[][] grades = {
                {5, 4, 5},
                {3, 3, 4},
                {5, 5, 5},
                {4, 4, 3}
        };
        System.out.println("=== Таблица успеваемости ===");
        System.out.println("Студент \tМат \tФиз \tПрог");
        for (int i = 0; i < grades.length; i++) {
            System.out.print("Студент " + (i + 1) + ":\t");
            for (int j = 0; j < grades[i].length; j++) {
                System.out.print(grades[i][j] + "\t\t");
            }
            System.out.println();
        }

        System.out.println("\n=== Средний балл студентов ===");
        double[] studentAverages = new double[4];
        for (int i = 0; i < grades.length; i++) {
            int sum = 0;
            for (int j = 0; j < grades[i].length; j++) {
                sum += grades[i][j];
            }
            studentAverages[i] = (double) sum / grades[i].length;
            System.out.println("Студент " + (i + 1) + ": " + String.format("%.1f", studentAverages[i]));
        System.out.println();
        }

        System.out.println("\n=== Средний балл по предметам ===");
        double[] subjectAverages = new double[3];
        for (int j = 0; j < 3; j++) {
            int sum = 0;
            for (int i = 0; i < 4; i++) {
                sum += grades[i][j];
            }
            subjectAverages[j] = (double) sum / 4;
            System.out.println(subjects[j] + ": " + String.format("%.1f", subjectAverages[j]));
        }

        System.out.println("\n=== Итоги и рекорды ===");

        double maxStudentAvg = studentAverages[0];
        int bestStudentIndex = 0;
        for (int i = 1; i < studentAverages.length; i++) {
            if (studentAverages[i] > maxStudentAvg) {
                maxStudentAvg = studentAverages[i];
                bestStudentIndex = i;
            }
        }
        System.out.println("Лучший студент: Студент " + (bestStudentIndex + 1) + " (Балл: " + String.format("%.1f", maxStudentAvg) + ")");

        double maxSubjectAvg = subjectAverages[0];
        int bestSubjectIndex = 0;
        for (int j = 1; j < subjectAverages.length; j++) {
            if (subjectAverages[j] > maxSubjectAvg) {
                maxSubjectAvg = subjectAverages[j];
                bestSubjectIndex = j;
            }
        }
        System.out.println("Самый успешный предмет: " + subjects[bestSubjectIndex] + " (Балл: " + String.format("%.1f", maxSubjectAvg) + ")");

        int excellentCount = 0;
        for (int i = 0; i < studentAverages.length; i++) {
            if (studentAverages[i] >= 4.5) {
                excellentCount = excellentCount + 1;
            }
        }
        System.out.println("Количество отличников (балл >= 4.5): " + excellentCount);
    }
}