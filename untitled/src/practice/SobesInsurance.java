package practice;

public class SobesInsurance {

    public static void main(String[] args) {
        // Создаем объект автомобильной страховки, но кладем в базовую переменную
        Insurance current = new CarInsurance();
        int premium = current.premium();
        String category = current.category(); // IDEA здесь заботливо подсветит желтым
        System.out.println("premium : " + premium);
        System.out.println("category : " + category);
    }
    static class Insurance {
        public static final int LOW = 100;

        // Обычный метод (переопределяется)
        public int premium() {
            return LOW;
        }
        // Статический метод (НЕ переопределяется, а скрывается)
        public static String category() {
            return "Insurance";
        }
    }

    static class CarInsurance extends Insurance {
        public static final int HIGH = 200;

        // Переопределяем обычный метод
        @Override
        public int premium() {
            return HIGH;
        }

        // Пытаемся "переопределить" статический метод (на самом деле просто прячем родительский)
        // Аннотацию @Override сюда повесить нельзя — будет ошибка компиляции!
        public static String category() {
            return "Car Insurance";
        }
    }
}