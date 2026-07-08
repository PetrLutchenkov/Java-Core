package homework.week_6;

abstract class Transport {
    String name;

    Transport (String name) {
        this.name = name;
    }

    public void printInfo() {
        System.out.println( name + " начинает движение");
    }

    public abstract void move();
}
class Car extends Transport {
    Car(String name) {
        super(name);
    }
    @Override
    public void move() {
        System.out.println(name + " едет по шоссе на 4 колесах.");
    }
}
class Bicycle extends Transport {
    Bicycle(String name){
        super(name);
    }
    @Override
    public void move() {
        System.out.println(name + " катится по велодорожке, крутим педали.");
    }
}
class Train extends Transport {
    Train(String name) {
        super(name);
    }
    @Override
    public void move() {
        System.out.println(name + " мчится по стальным рельсам.");
    }
}

class Main3 {
    static void main(String[] args) {

        Transport[] myTransports = {
                new Car("Автомобиль"),
                new Bicycle("Велосипед"),
                new Train("Поезд")
        };

        System.out.println("=== ТЕСТ-ДРАЙВ ===\n");

        for (Transport t : myTransports) {
            t.printInfo();
            t.move();
            System.out.println("-----------------------------------");
        }
    }
}