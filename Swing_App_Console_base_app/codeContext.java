//only using scanner list and array list libraries
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

class User {
    private String email;
    private String password;
    private String fullName;

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }
}

class FileHandler {
    public static boolean authenticateUser(String email, String password) {
        try {
            File file = new File("users.txt");
            FileReader fileReader = new FileReader(file);
            int ch;
            String content = "";
            while ((ch = fileReader.read()) != -1) {
                content += (char) ch;
            }
            fileReader.close();
            String[] users = content.split("\n");
            for (String user : users) {
                String[] userData = user.split(",");
                if (userData[0].equals(email) && userData[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static User getUserByEmail(String email) {
        try {
            File file = new File("users.txt");
            FileReader fileReader = new FileReader(file);
            int ch;
            String content = "";
            while ((ch = fileReader.read()) != -1) {
                content += (char) ch;
            }
            fileReader.close();
            String[] users = content.split("\n");
            for (String user : users) {
                String[] userData = user.split(",");
                if (userData[0].equals(email)) {
                    return new User(userData[0], userData[1], userData[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static void saveUser(User user) {
        try {
            File file = new File("users.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(user.getEmail() + "," + user.getPassword() + "," + user.getFullName() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
        System.out.print("Enter your choice (1 - Login, 2 - Signup): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            loginUser(email, password);
        } else if (choice == 2) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter full name: ");
            String fullName = scanner.nextLine();
            signupUser(email, password, fullName);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private void loginUser(String email, String password) {
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your password.");
            return;
        }

        if (FileHandler.authenticateUser(email, password)) {
            User user = FileHandler.getUserByEmail(email);
            String welcomeMessage = user != null ? 
                "Welcome back, " + user.getFullName() + "!" :
                "Welcome, Administrator!";
            JOptionPane.showMessageDialog(null, welcomeMessage);
            isLoggedIn = true;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.");
        }
    }

    private void signupUser(String email, String password, String fullName) {
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Password must be at least 6 characters long.");
            return;
        }

        if (fullName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your full name.");
            return;
        }

        // Check if email already exists
        User existingUser = FileHandler.getUserByEmail(email);
        if (existingUser != null) {
            JOptionPane.showMessageDialog(null, "An account with this email already exists. Please login instead.");
            return;
        }

        // Create and save new user
        User newUser = new User(email, password, fullName);
        FileHandler.saveUser(newUser);
        JOptionPane.showMessageDialog(null, "Account created successfully! Please login with your email and password.");
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
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