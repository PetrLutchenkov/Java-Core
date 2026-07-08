package homework.week_5;

/*
Encapsulation

Создайте класс Student с приватными полями: name и grade.
Сделайте геттеры и сеттеры для обоих полей.
В сеттере для grade добавьте проверку: оценка должна быть от 0 до 100.
Создайте несколько студентов в main, присвойте им оценки через сеттер и выведите через геттер.
Попробуйте присвоить некорректное значение и убедитесь, что проверка работает.
 */

class StudentInt {
    private int grade;
    private String name;

    public StudentInt(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public void setName() {
        this.name = name;
    }

    public void setGrade(int grade) {
        if (grade > 0 && grade <= 100) {
            this.grade = grade;
            return;
        }
        System.out.println("Оценка должна быть от 1 до 100");
    }
    public void PrintStudent() {
        System.out.println("Student: " + name + ", grage "+ grade);
    }

    static void main() {
        StudentInt student1 = new StudentInt("Andrew", 55);
        StudentInt student2 = new StudentInt("Max", 30);
        student1.PrintStudent();
        student2.PrintStudent();

        student1.setGrade(99);

        System.out.println("Student 1 grade: " + student1.getGrade());

        student1.setGrade(134);
    }
}
