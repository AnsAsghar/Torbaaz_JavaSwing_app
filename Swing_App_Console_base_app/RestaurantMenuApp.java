//only using scanner list and array list libraries
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.Box;
import java.awt.event.ActionListener;
import javax.swing.JButton;

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
    private String username;
    private String email;
    private String password;
    private String fullName;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = "";
    }

    public String getUsername() {
        return username;
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

    public static boolean emailExists(String email) {
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
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static List<MenuItem> loadMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        try {
            File file = new File("menu.txt");
            FileReader fileReader = new FileReader(file);
            int ch;
            String content = "";
            while ((ch = fileReader.read()) != -1) {
                content += (char) ch;
            }
            fileReader.close();
            String[] items = content.split("\n");
            for (String item : items) {
                String[] itemData = item.split(",");
                if (itemData.length == 6) {
                    int id = Integer.parseInt(itemData[0]);
                    String name = itemData[1];
                    double price = Double.parseDouble(itemData[2]);
                    String category = itemData[3];
                    String description = itemData[4];
                    boolean isAvailable = Boolean.parseBoolean(itemData[5]);
                    menuItems.add(new MenuItem(id, name, price, category, description, isAvailable));
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return menuItems;
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
        while (true) {
            if (!isLoggedIn) {
                int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Welcome to Torbaaz Restaurant",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Login", "Signup"}, "Login");
                if (choice == 0) {
                    showLoginDialog();
                } else if (choice == 1) {
                    showSignupDialog();
                }
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginDialog() {
        JTextField emailField = new JTextField(5);
        JPasswordField passwordField = new JPasswordField(5);

        JPanel loginPanel = new JPanel();
        loginPanel.add(new JLabel("Email:"));
        loginPanel.add(emailField);
        loginPanel.add(Box.createHorizontalStrut(15)); // a spacer
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, loginPanel, 
                 "Login", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            loginUser(email, password);
        }
    }

    private void showSignupDialog() {
        JTextField usernameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);

        JPanel signupPanel = new JPanel();
        signupPanel.add(new JLabel("Username:"));
        signupPanel.add(usernameField);
        signupPanel.add(Box.createHorizontalStrut(15));
        signupPanel.add(new JLabel("Email:"));
        signupPanel.add(emailField);
        signupPanel.add(Box.createHorizontalStrut(15));
        signupPanel.add(new JLabel("Password:"));
        signupPanel.add(passwordField);
        signupPanel.add(Box.createHorizontalStrut(15));
        signupPanel.add(new JLabel("Confirm Password:"));
        signupPanel.add(confirmPasswordField);

        int result = JOptionPane.showConfirmDialog(null, signupPanel, 
                 "Signup", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Validate inputs
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match.");
                return;
            }
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
                return;
            }
            if (FileHandler.emailExists(email)) {
                JOptionPane.showMessageDialog(null, "Email already exists.");
                return;
            }

            // Create and save new user
            User newUser = new User(username, email, password);
            FileHandler.saveUser(newUser);
            JOptionPane.showMessageDialog(null, "Account created successfully! Please log in with your email and password.");
            showLoginDialog();
        }
    }

    private void transitionToLogin() {
        JOptionPane.showMessageDialog(null, "Signup successful. Please log in.");
        showLoginDialog();
    }

    private void transitionToMainMenu() {
        showMainMenu();
    }

    private JTextField createTextField(String placeholder, int columns) {
        JTextField textField = new JTextField(columns);
        textField.setToolTipText(placeholder);
        return textField;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void signupUser(String email, String password) {
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Password must be at least 6 characters long.");
            return;
        }

        // Check if email already exists
        User existingUser = FileHandler.getUserByEmail(email);
        if (existingUser != null) {
            JOptionPane.showMessageDialog(null, "An account with this email already exists. Please login instead.");
            return;
        }

        // Create and save new user
        User newUser = new User("", email, password);
        FileHandler.saveUser(newUser);
        JOptionPane.showMessageDialog(null, "Account created successfully! Please login with your email and password.");

        // Prompt user to log in immediately after signup
        showLoginDialog();
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
            showMainMenu(); // Ensure this is called to display menu items
        } else {
            JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.");
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private void showMainMenu() {
        List<MenuItem> menuItems = FileHandler.loadMenuItems();
        StringBuilder menuDisplay = new StringBuilder("\n=== Menu Items ===\n");
        for (MenuItem item : menuItems) {
            menuDisplay.append(item.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, menuDisplay.toString(), "Menu", JOptionPane.INFORMATION_MESSAGE);
        // Keep the application running after displaying menu items
        start(); // Restart the process to allow further interaction
    }
    // main function
    public static void main(String[] args) {
        RestaurantMenuApp app = new RestaurantMenuApp();  // app here is just an object o the class 
        app.start();
    }
}