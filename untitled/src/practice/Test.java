package practice;

import javax.management.remote.JMXConnector;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> ips = Arrays.asList("192.168.0.1", "10.0.0.1");
        ips.stream().forEach(Main::connectSafely);
        ips.forEach(System.out::println);
        // 2. И теперь наш стрим выглядит идеально чисто!
        // Мы говорим: "Пройдись по всем IP и для каждого вызови метод connectSafely из класса Main"
//        ips.stream().forEach(Main::connectSafely);
    }

    // 1. Пишем наш метод-обертку ПРЯМО ЗДЕСЬ, внутри класса Main.
    // Он берет на себя удар проверяемого Exception.
    private static void connectSafely(String ipAddress) {
        try {
            JMXConnector NetworkUtils = null;
            NetworkUtils.connect();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось подключится к " + ipAddress, e);
        }
//        try {
//            // Пытаемся вызвать опасный метод из чужого класса
//            JMXConnector NetworkUtils = null;
//            NetworkUtils.connect();
//        } catch (Exception e) {
//            // Прячем Checked в Unchecked и сохраняем причину (e)
//            throw new RuntimeException("Не удалось подключиться к " + ipAddress, e);
//        }
    }
}