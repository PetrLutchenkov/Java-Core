package homework.week_1_4;

public class Task_6_1 {

    public static void main(String[] args) {
        // --- 1. Исходные данные ---
        String[] subjects = {"Математика", "Физика", "Программирование"};
        int[][] grades = {
                {5, 4, 5}, // Студент 1
                {3, 3, 4}, // Студент 2
                {5, 5, 5}, // Студент 3
                {4, 4, 3}  // Студент 4
        };

        // --- 2. Вычисления (работают "математики") ---
        double[] studentAverages = calculateStudentAverages(grades);
        double[] subjectAverages = calculateSubjectAverages(grades);

        // Находим индексы (номера) лучших результатов
        int bestStudentIndex = findBestIndex(studentAverages);
        int bestSubjectIndex = findBestIndex(subjectAverages);

        int excellentCount = countExcellentStudents(studentAverages);

        // --- 3. Вывод на экран (работают "дикторы") ---
        printTable(grades);
        printStudentAverages(studentAverages);
        printSubjectAverages(subjects, subjectAverages);

        System.out.println("\n=== Итоги и рекорды ===");
        printBestResult("Лучший студент", "Студент " + (bestStudentIndex + 1), studentAverages[bestStudentIndex]);
        printBestResult("Лучший предмет", subjects[bestSubjectIndex], subjectAverages[bestSubjectIndex]);
        System.out.println("Количество отличников (балл >= 4.5): " + excellentCount);
    }

    // ==========================================
    // БЛОК ВЫЧИСЛЕНИЙ (Только математика)
    // ==========================================

    // Считает средний балл каждого студента (по строкам)
    static double[] calculateStudentAverages(int[][] matrix) {
        double[] averages = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j];
            }
            averages[i] = (double) sum / matrix[i].length;
        }
        return averages;
    }

    // Считает средний балл по каждому предмету (по колонкам)
    static double[] calculateSubjectAverages(int[][] matrix) {
        int numSubjects = matrix[0].length; // Количество колонок
        double[] averages = new double[numSubjects];

        for (int j = 0; j < numSubjects; j++) {
            int sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                sum += matrix[i][j];
            }
            averages[j] = (double) sum / matrix.length;
        }
        return averages;
    }

    // Универсальный метод: находит индекс максимального числа в любом массиве double
    static int findBestIndex(double[] averages) {
        int bestIndex = 0;
        double max = averages[0];
        for (int i = 1; i < averages.length; i++) {
            if (averages[i] > max) {
                max = averages[i];
                bestIndex = i;
            }
        }
        return bestIndex; // Возвращаем именно номер (индекс), а не сам балл
    }

    // Считает количество студентов с баллом 4.5 и выше
    static int countExcellentStudents(double[] averages) {
        int count = 0;
        for (int i = 0; i < averages.length; i++) {
            if (averages[i] >= 4.5) {
                count++;
            }
        }
        return count;
    }

    // ==========================================
    // БЛОК ВЫВОДА (Только печать на экран)
    // ==========================================

    static void printTable(int[][] grades) {
        System.out.println("=== Таблица успеваемости ===");
        System.out.println("Студент \tМат \tФиз \tПрог");
        for (int i = 0; i < grades.length; i++) {
            System.out.print("Студент " + (i + 1) + ":\t");
            for (int j = 0; j < grades[i].length; j++) {
                System.out.print(grades[i][j] + "\t\t");
            }
            System.out.println();
        }
    }

    static void printStudentAverages(double[] averages) {
        System.out.println("\n=== Средний балл студентов ===");
        for (int i = 0; i < averages.length; i++) {
            System.out.printf("Студент %d: %.1f%n", (i + 1), averages[i]);
        }
    }

    static void printSubjectAverages(String[] subjects, double[] averages) {
        System.out.println("\n=== Средний балл по предметам ===");
        for (int i = 0; i < averages.length; i++) {
            System.out.printf("%s: %.1f%n", subjects[i], averages[i]);
        }
    }

    // Универсальный метод для красивого вывода любого рекорда
    static void printBestResult(String title, String name, double score) {
        System.out.printf("%s: %s (Балл: %.1f)%n", title, name, score);
    }
}