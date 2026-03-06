package homework;

public class Task_2 {
    public static void main(String[] args) {

        /*
        Задача 1: Создайте форматированную строку, которая представляет
        визитку с вашими данными: имя, профессия, email и т.д.
         */

        System.out.println("=== Задача 1: Визитка ===");

        String name = "Александр";
        String profession = "Java-разработчик";
        String email = "alex@example.com";
        String phone = "+7 (999) 123-45-67";

        String BusinessCard = String.format("""
                *********************************
                * Имя:        %s
                * Профессия:  %s
                * Email:      %s
                * Телефон:    %s
                *********************************
                """, name, profession, email, phone);

        System.out.println(BusinessCard);

        /*
        Задача 2: Создайте шаблон для вывода информации о погоде: город, температура,
        влажность, скорость ветра. Используйте метод formatted().
         */

        System.out.println("=== Задача 2: Погода ===");

        String city = "Амстердам";
        double temp = 15.5;
        int humidity = 72;
        double windSpeed = 4.8;

        String weatherInfo = "Погода в г. %s:\n - Температура: %.1f °C\n - Влажность: %d%%\n - Ветер: %.1f м/с\n"
                .formatted(city, temp, humidity, windSpeed);

        System.out.println(weatherInfo);
    }
}