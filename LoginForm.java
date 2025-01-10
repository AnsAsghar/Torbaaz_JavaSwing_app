import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private FileHandler fileHandler;
    private Color backgroundColor = new Color(18, 18, 18);
    private Color accentColor = new Color(90, 61, 171);

    public LoginForm(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        setupUI();
    }

    private void setupUI() {
        setTitle("Login - TORBAAZ APP");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Logo or Title
        JLabel titleLabel = new JLabel("TORBAAZ APP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Email field
        JPanel emailPanel = new JPanel(new BorderLayout(10, 10));
        emailPanel.setBackground(backgroundColor);
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 35));
        styleTextField(emailField);
        emailPanel.add(emailLabel, BorderLayout.NORTH);
        emailPanel.add(emailField, BorderLayout.CENTER);

        // Password field
        JPanel passwordPanel = new JPanel(new BorderLayout(10, 10));
        passwordPanel.setBackground(backgroundColor);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 35));
        styleTextField(passwordField);
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        // Login button
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(e -> handleLogin());

        // Register link
        JButton registerLink = new JButton("Don't have an account? Register");
        registerLink.setForeground(accentColor);
        registerLink.setBorderPainted(false);
        registerLink.setContentAreaFilled(false);
        registerLink.setFocusPainted(false);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerLink.addActionListener(e -> {
            new SignupForm(fileHandler);
            dispose();
        });

        // Add components
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(emailPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(registerLink);

        add(mainPanel);
        setVisible(true);
    }

    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(30, 30, 30));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 40, 40)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleButton(JButton button) {
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!fileHandler.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid email address",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fileHandler.validateLogin(email, password)) {
            String userName = fileHandler.getNameByEmail(email);
            new MenuPage(userName);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid email or password",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
