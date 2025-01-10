import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignupForm extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private FileHandler fileHandler;
    private Color backgroundColor = new Color(18, 18, 18);
    private Color accentColor = new Color(90, 61, 171);

    public SignupForm(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        setupUI();
    }

    private void setupUI() {
        setTitle("Sign Up - TORBAAZ APP");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Name field
        JPanel namePanel = new JPanel(new BorderLayout(10, 10));
        namePanel.setBackground(backgroundColor);
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 35));
        styleTextField(nameField);
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(nameField, BorderLayout.CENTER);

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

        // Confirm Password field
        JPanel confirmPanel = new JPanel(new BorderLayout(10, 10));
        confirmPanel.setBackground(backgroundColor);
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setForeground(Color.WHITE);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200, 35));
        styleTextField(confirmPasswordField);
        confirmPanel.add(confirmLabel, BorderLayout.NORTH);
        confirmPanel.add(confirmPasswordField, BorderLayout.CENTER);

        // Sign Up button
        JButton signupButton = new JButton("Sign Up");
        styleButton(signupButton);
        signupButton.addActionListener(e -> handleSignup());

        // Login link
        JButton loginLink = new JButton("Already have an account? Login");
        loginLink.setForeground(accentColor);
        loginLink.setBorderPainted(false);
        loginLink.setContentAreaFilled(false);
        loginLink.setFocusPainted(false);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLink.addActionListener(e -> {
            new LoginForm(fileHandler);
            dispose();
        });

        // Add components
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(namePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(emailPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(confirmPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(signupButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(loginLink);

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

    private void handleSignup() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validate all fields are filled
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!fileHandler.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid email address",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if email is already registered
        if (fileHandler.isEmailRegistered(email)) {
            JOptionPane.showMessageDialog(this,
                "This email is already registered",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "Passwords do not match",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate password strength (at least 6 characters)
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "Password must be at least 6 characters long",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add user
        if (fileHandler.addUser(name, email, password)) {
            JOptionPane.showMessageDialog(this,
                "Registration successful! Please login.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            new LoginForm(fileHandler);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error registering user. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
