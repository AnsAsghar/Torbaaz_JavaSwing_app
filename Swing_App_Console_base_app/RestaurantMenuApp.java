//only using scanner list and array list libraries
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
/*
// swift libraries that we commented 
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
*/
// MenuItem class to represent menu items with categories



class MenuItem 
{
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
    //using built in library(of STRING ) and using function toString()
    // 
    @Override 
    public String toString() 
    {
        return String.format("ID: %d | %s - Rs%.2f | %s", id, name, price, isAvailable ? "Available" : "Not Available");
    }
}

public class RestaurantMenuApp {
    /// creating list of class MENU ITEMS
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
       
        menuItems.add(new MenuItem(1, "Chicken Burger", 350, "Burgers", "Juicy chicken patty with lettuce and mayo", true));
        menuItems.add(new MenuItem(2, "Mighty Burger", 600, "Burgers", "Premium beef patty with cheese", true));
        menuItems.add(new MenuItem(3, "French Fries Large", 350, "Sides", "Crispy golden fries", true));
        menuItems.add(new MenuItem(4, "Meet N Eat Special Beef Burger", 600, "Beverages", "Refreshing cola drink", true));
        menuItems.add(new MenuItem(5, "Meet N Eat special Wrap -",  550, "Desserts", "Vanilla ice cream", true));
    }

    public void start() {
        //simple while loop to check if logged in
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
        if (username.equals(username) && password.equals(password)) {
            isLoggedIn = true;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void showMainMenu() {
        // menu items
        System.out.println("\n=== Torbaaz Restaurant Menu ===");
        System.out.println("1. View All Items");
        System.out.println("2. View Items by Category");
        System.out.println("3. Search Item");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        

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
        System.out.println("1. classic Burgers detials");
        System.out.println("2. Sides");
        System.out.println("3. Special Burger");
        System.out.println("4. Wraps");
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
        // for loop here for iteratng through the item object of MenuItem class
        for (MenuItem item : menuItems) 
        {
            // these are functions already built in  java
            if (item.getName().toLowerCase().contains(searchTerm)) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items found matching your search.");
        }
    }
    // simple log out function
    private void logout() {
        isLoggedIn = false;
        System.out.println("Logged out successfully.");
    }
    // main function
    public static void main(String[] args) {
        RestaurantMenuApp app = new RestaurantMenuApp();  // app here is just an object o the class 
        app.start();
    }
}