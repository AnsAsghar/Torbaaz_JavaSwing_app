import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuPage extends JFrame {
    private String username;
    private JPanel menuPanel;
    private ArrayList<FoodItem> menuItems;
    private String currentCategory = "All";
    private JPanel filterPanel;
    private Color selectedButtonColor = new Color(0, 153, 153);
    private Color defaultButtonColor = new Color(240, 240, 240);
    private List<CartItem> cartItems = new ArrayList<>();
    private JLabel cartCountLabel;

    public MenuPage(String username) {
        this.username = username;
        initializeMenu();
        setupUI();
    }

    private void initializeMenu() {
        menuItems = new ArrayList<>();
        menuItems.add(new FoodItem("Margherita Pizza", "Classic tomato sauce and mozzarella", 299.0, "Pizza", "Images/pizza.jpg"));
        menuItems.add(new FoodItem("Pepperoni Pizza", "Spicy pepperoni with cheese", 349.0, "Pizza", "Images/pepporonipizza.jpg"));
        menuItems.add(new FoodItem("Four Cheese Pizza", "Blend of four premium cheeses", 379.0, "Pizza", "Images/cheesepizza.jpg"));
        menuItems.add(new FoodItem("Classic Burger", "Beef patty with cheese", 199.0, "Burger", "Images/burger.jpg"));
        menuItems.add(new FoodItem("Chicken Burger", "Grilled chicken with lettuce", 179.0, "Burger", "Images/chickenburger.jpg"));
        menuItems.add(new FoodItem("Garlic Bread", "Toasted bread with garlic butter", 99.0, "Appetizers", "Images/garlicbread.jpg"));
        menuItems.add(new FoodItem("Onion Rings", "Crispy fried onion rings", 89.0, "Appetizers", "Images/ringonions.jpg"));
        menuItems.add(new FoodItem("Alfredo Pasta", "Creamy white sauce pasta", 249.0, "Pasta", "Images/alfredopasta.jpg"));
        menuItems.add(new FoodItem("Arrabbiata Pasta", "Spicy tomato sauce pasta", 229.0, "Pasta", "Images/pasta2.jpg"));
    }

    private void setupUI() {
        setTitle("Menu - Welcome " + username);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartPanel.setBackground(new Color(240, 240, 240));
        
        cartCountLabel = new JLabel("Cart (0)");
        cartCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton cartButton = new JButton("View Cart");
        cartButton.setBackground(new Color(0, 153, 153));
        cartButton.setForeground(Color.WHITE);
        cartButton.addActionListener(e -> showCart());
        
        cartPanel.add(cartCountLabel);
        cartPanel.add(cartButton);
        
        createFilterBar();
        
        topPanel.add(cartPanel, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        menuPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        menuPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(240, 240, 240));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginForm(new FileHandler()).setVisible(true);
        });

        bottomPanel.add(logoutButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        displayMenuItems();
        setVisible(true);
    }

    private void addToCart(FoodItem item) {
        Optional<CartItem> existingItem = cartItems.stream()
            .filter(cartItem -> cartItem.getFoodItem().getName().equals(item.getName()))
            .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + 1);
        } else {
            cartItems.add(new CartItem(item, 1));
        }
        
        updateCartCount();
        JOptionPane.showMessageDialog(this, 
            item.getName() + " added to cart!", 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateCartCount() {
        int totalItems = cartItems.stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
        cartCountLabel.setText("Cart (" + totalItems + ")");
    }

    private void showCart() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Your cart is empty!",
                "Empty Cart",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        CartDialog dialog = new CartDialog(this, cartItems);
        dialog.setVisible(true);
        updateCartCount();
    }

    private void createFilterBar() {
        filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBackground(new Color(240, 240, 240));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonPanel.setBackground(new Color(240, 240, 240));

        String[] categories = {
            "All", "Appetizers", "Pizza", "Burger", "Pasta"
        };

        for (String category : categories) {
            JButton categoryButton = createFilterButton(category);
            buttonPanel.add(categoryButton);
        }

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(Color.GRAY);

        filterPanel.add(buttonPanel);
        filterPanel.add(separator);
    }

    private JButton createFilterButton(String category) {
        JButton button = new JButton(category);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        
        updateButtonAppearance(button, category.equals(currentCategory));

        button.addActionListener(e -> {
            currentCategory = category;
            updateAllButtonAppearances();
            displayMenuItems();
        });

        return button;
    }

    private void updateButtonAppearance(JButton button, boolean isSelected) {
        if (isSelected) {
            button.setBackground(selectedButtonColor);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(defaultButtonColor);
            button.setForeground(Color.BLACK);
        }
    }

    private void updateAllButtonAppearances() {
        for (Component comp : ((JPanel)filterPanel.getComponent(0)).getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                updateButtonAppearance(button, button.getText().equals(currentCategory));
            }
        }
    }

    private void displayMenuItems() {
        menuPanel.removeAll();
        for (FoodItem item : menuItems) {
            if (currentCategory.equals("All") || item.getCategory().equals(currentCategory)) {
                menuPanel.add(createItemPanel(item));
            }
        }
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private JPanel createItemPanel(FoodItem item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);

        // Add image
        try {
            ImageIcon originalIcon = new ImageIcon(item.getImagePath());
            Image scaledImage = originalIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(imageLabel);
            panel.add(Box.createVerticalStrut(10));
        } catch (Exception e) {
            System.out.println("Error loading image: " + item.getImagePath());
        }

        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><div style='width:200px;text-align:center'>" + 
            item.getDescription() + "</div></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel(String.format("₹%.2f", item.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(new Color(76, 175, 80));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton orderButton = new JButton("Add to Cart");
        orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderButton.setBackground(new Color(76, 175, 80));
        orderButton.setForeground(Color.WHITE);
        orderButton.setFocusPainted(false);
        orderButton.addActionListener(e -> addToCart(item));

        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(descLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(priceLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(orderButton);

        return panel;
    }
}
