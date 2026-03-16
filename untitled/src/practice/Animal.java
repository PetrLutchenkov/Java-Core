package practice;

// Родительский класс
public class Animal {
    public void makeSound() {
        System.out.println("Животное издаёт звук");
    }
}

// Дочерние классы
class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Гав-гав");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Мяу");
    }
}
class Main {
    public static void main(String[] args) {
        Animal dog = new Dog();  // тип Animal, объект Dog
        Animal cat = new Cat();  // тип Animal, объект Cat

        dog.makeSound();  // Гав-гав
        cat.makeSound();  // Мяу

        // Массив животных
        Animal[] animals = { dog, cat, new Animal() };

        for (Animal a : animals) {
            a.makeSound();  // У каждого своя реализация
        }
    }
}
