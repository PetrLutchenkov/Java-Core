package homework.week_5;

/*
Создайте класс Student с полями:

name
список оценок (ArrayList<Integer> grades)
Добавьте методы:

addGrade(int grade) — добавить оценку
printGrades() — вывести все оценки.
Создайте несколько студентов, добавьте им оценки и выведите их.

Создайте класс Classroom, который хранит список студентов, и метод printAllStudents(),
выводящий имена и оценки всех студентов.
 */

import java.util.ArrayList;

class Student {
    private String name;
    private ArrayList<Integer> grades;

    public Student(String name) {
        this.name = name;
        // КРИТИЧЕСКИ ВАЖНО: Мы обязаны "создать" пустой список при рождении студента.
        // Иначе некуда будет складывать оценки!
        this.grades = new ArrayList<>();
    }

    public void addGrade(int grade) {
        if (grade >= 1 && grade <= 5) {
            grades.add(grade);
        } else {
            System.out.println("⚠️ Ошибка: Оценка должна быть от 1 до 5.");
        }
    }

    public void printGrades() {
        System.out.println("Студент: " + name + " | Оценки: " + grades);
    }
}

class Classroom {
    private ArrayList<Student> students;

    public Classroom() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void printAllStudents() {
        System.out.println("\n=== ЖУРНАЛ УСПЕВАЕМОСТИ КЛАССА ===");
        for (Student currentStudent : students) {
            currentStudent.printGrades();
        }
        System.out.println("==================================\n");
    }
}

class Main {
    public static void main(String[] args) {

        Student student1 = new Student("Гермиона Грейнджер");
        Student student2 = new Student("Рон Уизли");
        Student student3 = new Student("Гарри Поттер");

        student1.addGrade(5);
        student1.addGrade(5);
        student1.addGrade(5);

        student2.addGrade(3);
        student2.addGrade(4);

        student3.addGrade(4);
        student3.addGrade(5);
        student3.addGrade(8);

        Classroom hogwartsClass = new Classroom();

        hogwartsClass.addStudent(student1);
        hogwartsClass.addStudent(student2);
        hogwartsClass.addStudent(student3);

        hogwartsClass.printAllStudents();
    }
}
