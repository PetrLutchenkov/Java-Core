package homework.week_6;

/*
Создайте класс Shape с методом draw().
Создайте дочерние классы: Circle, Square, Triangle, которые переопределяют draw().
Создайте массив или список фигур и вызовите draw() в цикле.
Каждая фигура должна нарисоваться по-своему.
Добавьте новую фигуру, не меняя существующий код.
 */

public class Shape {
    public void draw () {
        System.out.println("Paint something");
    }
}
class Circle extends Shape {
    @Override
    public void draw () {
        System.out.println("Paint the Circle");
    }
}
class Square extends Shape {
    @Override
    public void draw () {
        System.out.println("Paint the Square");
    }
}
class Triangle extends Shape {
    @Override
    public void draw () {
        System.out.println("Paint the Triangle");
    }
}
class Star extends Shape {
    @Override
    public void draw () {
        System.out.println("Paint the Star");
    }
}
class Main {
    static void main() {
        Shape[] shapes = { new Circle(), new Square(), new Triangle(), new Star() };
        for (Shape s : shapes) {
            s.draw();  // у каждой фигуры — своя реализация
        }
    }
}