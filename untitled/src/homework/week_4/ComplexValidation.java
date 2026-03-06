package homework.week_4;

/*
        Дополнительное задание: Комплексная система проверок
        Реализуйте систему проверки для вождения автомобиля:
        - Возраст должен быть >= 18
        - Наличие водительских прав
        - Опыт вождения >= 2 лет
        - Выведите соответствующие сообщения об ошибках
 */

public class ComplexValidation {
    public static void main(String[] args) {
        int age = 25;
        boolean hasLicense = true;
        int experience = 3;

        System.out.println("\n=== Проверка допуска к вождению ===");

        if (age < 18) {
            System.out.println("Отказ: Лицам младше 18 лет управление запрещено.");
        } else if (!hasLicense) {
            System.out.println("Отказ: У вас нет водительских прав.");
        } else if (experience < 2) {
            System.out.println("Отказ: Недостаточный стаж вождения (минимум 2 года). Ваш стаж: " + experience);
        } else {
            System.out.println("Успешно: Доступ к аренде автомобиля разрешен. Счастливого пути!");
        }
    }
}