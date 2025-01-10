import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

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
}

class User {
    private String username;
    private String password;
    private String email;
    private String fullName;

    public User(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
}

class FileHandler {
    public static List<User> loadUsers() {
        // Implement logic to load users from file
        return new ArrayList<>();
    }

    public static void saveUsers(List<User> users) {
        // Implement logic to save users to file
    }
}

public class RestaurantMenuApp extends JFrame {
    // Add constants for consistent styling
    private static final int PADDING = 15;
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Color PRIMARY_COLOR = new Color(255, 87, 34); // Vibrant orange
    private static final Color SECONDARY_COLOR = new Color(33, 150, 243); // Bright blue
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Light grey
    private static final Color DARK_PRIMARY_COLOR = new Color(45, 45, 45); // Dark grey
    private static final Color DARK_SECONDARY_COLOR = new Color(60, 63, 65); // Slightly lighter grey
    private static final Color LIGHT_TEXT_COLOR = new Color(230, 230, 230); // Light grey for text
    private static final int IMAGE_SIZE = 150;

    private String currentCategory = "All Items";
    private JPanel contentPanel;

    // Constructor remains the same
    public RestaurantMenuApp() {
        setTitle("Torbaaz - Restaurant Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        showLoginPage();
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(PADDING/2, PADDING, PADDING/2, PADDING));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    private void showLoginPage() {
        getContentPane().removeAll();
        
        // Create main panel with BorderLayout instead of null
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(DARK_PRIMARY_COLOR);
        
        // Login panel with semi-transparent effect
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 255, 255, 220));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Components remain the same
        JLabel titleLabel = new JLabel("Welcome to Torbaaz");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(Color.BLACK);
        
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(PRIMARY_COLOR);
        loginButton.setForeground(Color.BLACK);
        
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBackground(SECONDARY_COLOR);
        signupButton.setForeground(Color.BLACK);
        
        // Layout components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        loginPanel.add(titleLabel, gbc);
        loginPanel.add(Box.createVerticalStrut(20), gbc);
        loginPanel.add(new JLabel("Username:"), gbc);
        loginPanel.add(usernameField, gbc);
        loginPanel.add(new JLabel("Password:"), gbc);
        loginPanel.add(passwordField, gbc);
        loginPanel.add(Box.createVerticalStrut(10), gbc);
        loginPanel.add(loginButton, gbc);
        loginPanel.add(Box.createVerticalStrut(5), gbc);
        loginPanel.add(signupButton, gbc);
        
        // Add login panel to the center of main panel
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        
        // Set main panel as content pane
        setContentPane(mainPanel);
        
        // Add action listeners
        loginButton.addActionListener(event -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            loginUser(username, password);
        });
        signupButton.addActionListener(action -> {
            if (action != null) {
                showSignupPage();
            }
        });
        
        revalidate();
        repaint();
    }

    
    private void showSignupPage() {
        getContentPane().removeAll();
        
        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(DARK_PRIMARY_COLOR);
        
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new GridBagLayout());
        signupPanel.setBackground(new Color(255, 255, 255, 220));
        signupPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Components
        JLabel signupTitleLabel = new JLabel("Create New Account");
        signupTitleLabel.setFont(HEADER_FONT);
        signupTitleLabel.setForeground(LIGHT_TEXT_COLOR);
        
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);
        
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBackground(SECONDARY_COLOR);
        createAccountButton.setForeground(Color.BLACK);
        
        JButton backButton = new JButton("Back to Login");
        backButton.setBackground(PRIMARY_COLOR);
        backButton.setForeground(Color.BLACK);
        
        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        signupPanel.add(signupTitleLabel, gbc);
        signupPanel.add(Box.createVerticalStrut(20), gbc);
        signupPanel.add(new JLabel("Full Name:"), gbc);
        signupPanel.add(nameField, gbc);
        signupPanel.add(new JLabel("Email:"), gbc);
        signupPanel.add(emailField, gbc);
        signupPanel.add(new JLabel("Password:"), gbc);
        signupPanel.add(passwordField, gbc);
        signupPanel.add(new JLabel("Confirm Password:"), gbc);
        signupPanel.add(confirmPasswordField, gbc);
        signupPanel.add(Box.createVerticalStrut(10), gbc);
        signupPanel.add(createAccountButton, gbc);
        signupPanel.add(Box.createVerticalStrut(5), gbc);
        signupPanel.add(backButton, gbc);
        
        // Add signup panel to the center of main panel
        mainPanel.add(signupPanel, BorderLayout.CENTER);
        
        signupPanel.add(backButton, gbc);
        
        createAccountButton.addActionListener(event -> {
            String username = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
                return;
            }
            signupUser(username, password, email, username);
        });
        backButton.addActionListener(action -> {
            if (action != null) {
                showLoginPage();
            }
        });
        add(mainPanel);
        
        revalidate();
        repaint();
    }
    
    private void loginUser(String username, String password) {
        // Hardcoded admin login
        if (username.equals("admin") && password.equals("p")) {
            JOptionPane.showMessageDialog(this, "Login successful. Welcome, admin!");
            showMainMenu();
            return;
        }

        // Existing logic for other users
        List<User> users = FileHandler.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful. Welcome, " + username + "!");
                showMainMenu();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
    }

    private void signupUser(String username, String password, String email, String fullName) {
        List<User> users = FileHandler.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please log in instead.");
                showLoginPage();
                return;
            }
        }
        users.add(new User(username, password, email, fullName));
        FileHandler.saveUsers(users);
        JOptionPane.showMessageDialog(this, "Sign up successful. You can now log in.");
        showLoginPage();
    }

    @SuppressWarnings("unused")
    private void showMainMenu() {
        getContentPane().removeAll();
        
        // Create main panel with a modern layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(DARK_PRIMARY_COLOR);
        
        // Top header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel restaurantName = new JLabel("Torbaaz");
        restaurantName.setFont(HEADER_FONT);
        restaurantName.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        
        headerPanel.add(restaurantName, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        // Side navigation panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(DARK_SECONDARY_COLOR);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        String[] categories = {"All Items", "Appetizers", "Main Course", "Desserts", "Beverages"};
        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setMaximumSize(new Dimension(200, 40));
            categoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Add action listener for category filtering
            categoryButton.addActionListener(e -> {
                currentCategory = category;
                updateMenuDisplay();
            });
            
            sidePanel.add(categoryButton);
            sidePanel.add(Box.createVerticalStrut(10));
        }
        
        // Create content panel for menu items
        contentPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        contentPanel.setBackground(DARK_PRIMARY_COLOR);
        contentPanel.setForeground(LIGHT_TEXT_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        
        // Initial display of menu items
        updateMenuDisplay();
        logoutButton.addActionListener(event -> {
            if (event != null) {
                showLoginPage();
            }
        });
        logoutButton.addActionListener(event -> showLoginPage());
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(sidePanel), BorderLayout.WEST);
        mainPanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);
        
        add(mainPanel);
        revalidate();
        repaint();
    }
    
    private JPanel createMenuItemPanel(String name, String price, String description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(IMAGE_SIZE + 2*PADDING, IMAGE_SIZE + 100));
        
        // Add hover effect to panel
        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    new EmptyBorder(PADDING-1, PADDING-1, PADDING-1, PADDING-1)
                ));
            }
            public void mouseExited(MouseEvent e) {
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    new EmptyBorder(PADDING, PADDING, PADDING, PADDING)
                ));
            }
        });

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(HEADER_FONT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(REGULAR_FONT);
        priceLabel.setForeground(SECONDARY_COLOR);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea descArea = new JTextArea(description);
        descArea.setFont(REGULAR_FONT);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);
        descArea.setBackground(Color.WHITE);
        descArea.setMaximumSize(new Dimension(IMAGE_SIZE, 50));
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton orderButton = new JButton("Add to Order");
        styleButton(orderButton, PRIMARY_COLOR);
        orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderButton.setToolTipText("Add this item to your order");

        // Add components with consistent spacing
        panel.add(Box.createVerticalStrut(PADDING));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(PADDING/2));
        panel.add(priceLabel);
        panel.add(Box.createVerticalStrut(PADDING/2));
        panel.add(descArea);
        panel.add(Box.createVerticalStrut(PADDING));
        panel.add(orderButton);
        panel.add(Box.createVerticalStrut(PADDING));
        
        return panel;
    }
    
    private void updateMenuDisplay() {
        contentPanel.removeAll();
        
        // Sample menu items with categories
        List<MenuItem> items = getMenuItemsByCategory(currentCategory);
        
        for (MenuItem item : items) {
            JPanel itemPanel = createMenuItemPanel(item.getName(), 
                String.format("$%.2f", item.getPrice()),
                item.getDescription());
            contentPanel.add(itemPanel);
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private List<MenuItem> getMenuItemsByCategory(String category) {
        if (category.equals("All Items")) {
            return getAllMenuItems();
        }
        
        List<MenuItem> filteredItems = new ArrayList<>();
        for (MenuItem item : getAllMenuItems()) {
            if (item.getCategory().equals(category)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    private List<MenuItem> getAllMenuItems() {
        // Sample menu items
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem(1, "Spring Rolls", 8.99, "Appetizers", "Crispy vegetable spring rolls", true));
        items.add(new MenuItem(2, "Chicken Biryani", 15.99, "Main Course", "Aromatic rice dish with chicken", true));
        items.add(new MenuItem(3, "Gulab Jamun", 5.99, "Desserts", "Sweet milk dumplings", true));
        items.add(new MenuItem(4, "Mango Lassi", 4.99, "Beverages", "Sweet mango yogurt drink", true));
        
        // Add more menu items
        items.add(new MenuItem(5, "Samosas", 7.99, "Appetizers", "Spicy potato-filled pastries", true));
        items.add(new MenuItem(6, "Butter Chicken", 16.99, "Main Course", "Creamy tomato-based chicken curry", true));
        items.add(new MenuItem(7, "Rasmalai", 6.99, "Desserts", "Soft cheese patties in sweet cream", true));
        items.add(new MenuItem(8, "Chai Tea", 3.99, "Beverages", "Traditional spiced Indian tea", true));
        
        return items;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            new RestaurantMenuApp().setVisible(true);
        });
    }
}