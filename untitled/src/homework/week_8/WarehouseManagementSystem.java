package homework.week_8;

/*
СИСТЕМА УПРАВЛЕНИЯ СКЛАДОМ С HASHSET.
Реализуйте систему управления складом товаров, использующую HashMap
для эффективного хранения и поиска товаров по различным критериям
 */

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

// Класс для представления товара
class Product {
    private String productId;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private LocalDate expirationDate;
    private String supplier;

    public Product(String productId, String name, String category, double price,
                   int quantity, LocalDate expirationDate, String supplier) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.supplier = supplier;
    }

    // Геттеры
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public String getSupplier() { return supplier; }

    // Сеттеры
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    @Override
    public String toString() {
        return String.format("Товар #%s: %s [%s] - %.2f руб, %d шт.",
                productId, name, category, price, quantity);
    }
}

// Основной класс системы управления складом
public class WarehouseManagementSystem {

    // Главное хранилище (O(1) доступ по ID)
    private HashMap<String, Product> productsById;

    // Вторичные индексы (O(1) доступ к спискам)
    private HashMap<String, List<Product>> productsByCategory;
    private HashMap<String, List<Product>> productsBySupplier;
    private HashMap<LocalDate, List<Product>> expiringProducts;

    public WarehouseManagementSystem() {
        this.productsById = new HashMap<>();
        this.productsByCategory = new HashMap<>();
        this.productsBySupplier = new HashMap<>();
        this.expiringProducts = new HashMap<>();
    }

    // ==========================================
    // Задание 1: Базовые операции с товарами
    // ==========================================

    public boolean addProduct(Product product) {
        if (product == null || productsById.containsKey(product.getProductId())) {
            return false;
        }

        productsById.put(product.getProductId(), product);
        productsByCategory.computeIfAbsent(product.getCategory(), k -> new ArrayList<>()).add(product);
        productsBySupplier.computeIfAbsent(product.getSupplier(), k -> new ArrayList<>()).add(product);
        expiringProducts.computeIfAbsent(product.getExpirationDate(), k -> new ArrayList<>()).add(product);

        return true;
    }

    public boolean removeProduct(String productId) {
        Product p = productsById.remove(productId);
        if (p == null) return false;

        // Удаляем товар из всех вторичных индексов
        productsByCategory.getOrDefault(p.getCategory(), new ArrayList<>()).remove(p);
        productsBySupplier.getOrDefault(p.getSupplier(), new ArrayList<>()).remove(p);
        expiringProducts.getOrDefault(p.getExpirationDate(), new ArrayList<>()).remove(p);

        return true;
    }

    public boolean updateProductQuantity(String productId, int newQuantity) {
        Product p = productsById.get(productId);
        if (p != null && newQuantity >= 0) {
            p.setQuantity(newQuantity);
            return true;
        }
        return false;
    }

    // ==========================================
    // Задание 2: Поиск и получение товаров
    // ==========================================

    public Product findProductById(String productId) {
        return productsById.get(productId);
    }

    public List<Product> getProductsByCategory(String category) {
        return new ArrayList<>(productsByCategory.getOrDefault(category, new ArrayList<>()));
    }

    public List<Product> getProductsBySupplier(String supplier) {
        return new ArrayList<>(productsBySupplier.getOrDefault(supplier, new ArrayList<>()));
    }

    public List<Product> searchProductsByName(String namePart) {
        String lowerCaseNamePart = namePart.toLowerCase();
        return productsById.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerCaseNamePart))
                .collect(Collectors.toList());
    }

    // ==========================================
    // Задание 3: Операции с инвентарем
    // ==========================================

    public boolean sellProduct(String productId, int quantity) {
        Product p = productsById.get(productId);
        if (p != null && p.getQuantity() >= quantity && quantity > 0) {
            p.setQuantity(p.getQuantity() - quantity);
            return true;
        }
        return false;
    }

    public boolean restockProduct(String productId, int quantity) {
        Product p = productsById.get(productId);
        if (p != null && quantity > 0) {
            p.setQuantity(p.getQuantity() + quantity);
            return true;
        }
        return false;
    }

    public List<Product> getLowStockProducts(int threshold) {
        return productsById.values().stream()
                .filter(p -> p.getQuantity() < threshold)
                .collect(Collectors.toList());
    }

    // ==========================================
    // Задание 4: Работа с датами истечения срока
    // ==========================================

    public List<Product> getExpiringProducts(LocalDate date) {
        return expiringProducts.entrySet().stream()
                .filter(entry -> !entry.getKey().isAfter(date))
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
    }

    public List<Product> getExpiredProducts() {
        return getExpiringProducts(LocalDate.now());
    }

    public int removeExpiredProducts() {
        List<Product> expired = getExpiredProducts();
        int count = 0;
        for (Product p : expired) {
            if (removeProduct(p.getProductId())) {
                count++;
            }
        }
        return count;
    }

    // ==========================================
    // Задание 5: Статистика и анализ
    // ==========================================

    public Map<String, Object> getWarehouseStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("Общее количество уникальных товаров", productsById.size());
        stats.put("Общее количество категорий", productsByCategory.size());
        stats.put("Общее количество поставщиков", productsBySupplier.size());

        double totalValue = productsById.values().stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();
        stats.put("Общая стоимость инвентаря", totalValue);

        return stats;
    }

    public Map<String, Double> getInventoryValueByCategory() {
        Map<String, Double> valueByCategory = new HashMap<>();
        for (Map.Entry<String, List<Product>> entry : productsByCategory.entrySet()) {
            double categoryValue = entry.getValue().stream()
                    .mapToDouble(p -> p.getPrice() * p.getQuantity())
                    .sum();
            valueByCategory.put(entry.getKey(), categoryValue);
        }
        return valueByCategory;
    }

    public List<Product> getMostExpensiveProducts(int count) {
        return productsById.values().stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    // ==========================================
    // Задание 6: Операции с поставщиками и категориями
    // ==========================================

    public Set<String> getAllSuppliers() {
        return new HashSet<>(productsBySupplier.keySet());
    }

    public Set<String> getAllCategories() {
        return new HashSet<>(productsByCategory.keySet());
    }

    public List<Product> getProductsInPriceRange(double minPrice, double maxPrice) {
        return productsById.values().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // ==========================================
    // Задание 7: Пакетные операции
    // ==========================================

    public int addProducts(List<Product> products) {
        int count = 0;
        for (Product p : products) {
            if (addProduct(p)) count++;
        }
        return count;
    }

    public int updatePricesForCategory(String category, double percentageIncrease) {
        List<Product> products = productsByCategory.get(category);
        if (products == null) return 0;

        for (Product p : products) {
            double newPrice = p.getPrice() * (1 + percentageIncrease / 100.0);
            p.setPrice(newPrice);
        }
        return products.size();
    }

    public int clearZeroQuantityProducts() {
        List<Product> zeroQty = productsById.values().stream()
                .filter(p -> p.getQuantity() == 0)
                .collect(Collectors.toList());

        for (Product p : zeroQty) {
            removeProduct(p.getProductId());
        }
        return zeroQty.size();
    }

    // ==========================================
    // Задание 8: Валидация и проверки
    // ==========================================

    public boolean validateDataIntegrity() {
        int mainSize = productsById.size();

        int categorySize = productsByCategory.values().stream().mapToInt(List::size).sum();
        if (mainSize != categorySize) return false;

        int supplierSize = productsBySupplier.values().stream().mapToInt(List::size).sum();
        if (mainSize != supplierSize) return false;

        return true;
    }

    public List<Product> findDuplicateProducts() {
        // Ищем товары с одинаковым именем и категорией (но разными ID)
        List<Product> duplicates = new ArrayList<>();
        Map<String, Product> uniqueCheck = new HashMap<>();

        for (Product p : productsById.values()) {
            String key = p.getName() + "|" + p.getCategory();
            if (uniqueCheck.containsKey(key)) {
                duplicates.add(p);
                // Добавляем и оригинал, если его еще нет в списке
                Product original = uniqueCheck.get(key);
                if (!duplicates.contains(original)) {
                    duplicates.add(original);
                }
            } else {
                uniqueCheck.put(key, p);
            }
        }
        return duplicates;
    }

    // ==========================================
    // Демонстрация работы системы
    // ==========================================

    public static void main(String[] args) {
        WarehouseManagementSystem system = new WarehouseManagementSystem();

        system.addProduct(new Product("P001", "Молоко", "Молочные продукты", 85.50, 100, LocalDate.of(2025, 12, 31), "Молокозавод №1"));
        system.addProduct(new Product("P002", "Хлеб", "Хлебобулочные изделия", 45.00, 50, LocalDate.of(2024, 10, 15), "Пекарня 'Свежий хлеб'"));
        system.addProduct(new Product("P003", "Сыр", "Молочные продукты", 320.00, 30, LocalDate.of(2025, 11, 20), "Молокозавод №1"));

        System.out.println("=== Поиск товара по ID ===");
        System.out.println("Найден: " + system.findProductById("P001"));

        System.out.println("\n=== Товары категории 'Молочные продукты' ===");
        system.getProductsByCategory("Молочные продукты").forEach(System.out::println);

        System.out.println("\n=== Продажа товара (10 шт Молока) ===");
        system.sellProduct("P001", 10);
        System.out.println("После продажи: " + system.findProductById("P001"));

        System.out.println("\n=== Проверка целостности данных ===");
        System.out.println("Данные согласованы: " + system.validateDataIntegrity());

        System.out.println("\n=== Статистика склада ===");
        system.getWarehouseStatistics().forEach((k, v) -> System.out.println(k + ": " + v));
    }
}