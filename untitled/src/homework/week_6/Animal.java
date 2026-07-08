package homework.week_6;

import java.util.Scanner;

class Animal {
    protected String name;
    public Animal(String name) {
        this.name = name;
    }

    // Базовый метод. Он как "шаблон"
    public void makeSound() {
        System.out.println(name + " издает непонятный звук...");
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " говорит гав");
    }

    public void run() {
        System.out.println(name + " бегает за мячиком");
    }
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " говорит мяу");
    }

    public void climb() {
        System.out.println(name + " лезет на штору");
    }
}

class Main2 {
    public static void main(String[] args) {
        Dog myDog = new Dog("Рэкс");
        Cat myCat = new Cat("Барсик");

        myDog.makeSound();
        myDog.run();
        myCat.makeSound();
        myCat.climb();
    }
}