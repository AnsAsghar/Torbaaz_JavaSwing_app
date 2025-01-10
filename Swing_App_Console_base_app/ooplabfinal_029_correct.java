import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// MenuItem class to represent menu items with categories
class MenuItem {
    private int id;
    private String name;
    private double price;
    private String category;
    private String description;
    private boolean isAvailable;

    public MenuItem(int id, String name, double price, String category, String description, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public boolean isAvailable() { return isAvailable; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s - $%.2f | %s", id, name, price, isAvailable ? "Available" : "Not Available");
    }
}

public class RestaurantMenuApp {
    private List<MenuItem> menuItems;
    private Scanner scanner;
    private boolean isLoggedIn;

    public RestaurantMenuApp() {
        this.menuItems = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.isLoggedIn = false;
        initializeMenu();
    }

    private void initializeMenu() {
        // Add some sample menu items
        menuItems.add(new MenuItem(1, "Chicken Burger", 8.99, "Burgers", "Juicy chicken patty with lettuce and mayo", true));
        menuItems.add(new MenuItem(2, "Beef Burger", 9.99, "Burgers", "Premium beef patty with cheese", true));
        menuItems.add(new MenuItem(3, "French Fries", 3.99, "Sides", "Crispy golden fries", true));
        menuItems.add(new MenuItem(4, "Cola", 1.99, "Beverages", "Refreshing cola drink", true));
        menuItems.add(new MenuItem(5, "Ice Cream", 4.99, "Desserts", "Vanilla ice cream", true));
    }

    public void start() {
        while (true) {
            if (!isLoggedIn) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n=== Welcome to Torbaaz Restaurant ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Simple login validation (you can enhance this)
        if (username.equals("admin") && password.equals("admin")) {
            isLoggedIn = true;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Torbaaz Restaurant Menu ===");
        System.out.println("1. View All Items");
        System.out.println("2. View Items by Category");
        System.out.println("3. Search Item");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                displayAllItems();
                break;
            case 2:
                showCategoryMenu();
                break;
            case 3:
                searchItem();
                break;
            case 4:
                logout();
                break;
            case 5:
                System.out.println("Thank you for using Torbaaz Restaurant Menu!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void displayAllItems() {
        System.out.println("\n=== All Menu Items ===");
        for (MenuItem item : menuItems) {
            System.out.println(item);
        }
    }

    private void showCategoryMenu() {
        System.out.println("\n=== Categories ===");
        System.out.println("1. Burgers");
        System.out.println("2. Sides");
        System.out.println("3. Beverages");
        System.out.println("4. Desserts");
        System.out.print("Enter category number: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String category;
        switch (choice) {
            case 1:
                category = "Burgers";
                break;
            case 2:
                category = "Sides";
                break;
            case 3:
                category = "Beverages";
                break;
            case 4:
                category = "Desserts";
                break;
            default:
                System.out.println("Invalid category.");
                return;
        }

        displayItemsByCategory(category);
    }

    private void displayItemsByCategory(String category) {
        System.out.println("\n=== " + category + " ===");
        boolean found = false;
        for (MenuItem item : menuItems) {
            if (item.getCategory().equals(category)) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items found in this category.");
        }
    }

    private void searchItem() {
        System.out.print("Enter item name to search: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        System.out.println("\n=== Search Results ===");
        boolean found = false;
        for (MenuItem item : menuItems) {
            if (item.getName().toLowerCase().contains(searchTerm)) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items found matching your search.");
        }
    }

    private void logout() {
        isLoggedIn = false;
        System.out.println("Logged out successfully.");
    }

    public static void main(String[] args) {
        RestaurantMenuApp app = new RestaurantMenuApp();
        app.start();
    }
}