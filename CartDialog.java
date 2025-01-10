import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CartDialog extends JDialog {
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private List<CartItem> cartItems;
    private JLabel totalLabel;

    public CartDialog(JFrame parent, List<CartItem> cartItems) {
        super(parent, "Shopping Cart", true);
        this.cartItems = cartItems;
        setupUI();
    }

    private void setupUI() {
        setSize(600, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        // Create table model
        String[] columns = {"Item", "Price", "Quantity", "Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only quantity is editable
            }
        };

        // Create table
        cartTable = new JTable(tableModel);
        cartTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(cartTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for total and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Total amount
        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton updateButton = new JButton("Update Cart");
        updateButton.addActionListener(e -> updateCart());
        
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(new Color(76, 175, 80));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Thank you for your order!\nTotal Amount: ₹" + calculateTotal(), 
                "Order Confirmed", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        buttonsPanel.add(updateButton);
        buttonsPanel.add(checkoutButton);
        bottomPanel.add(buttonsPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        updateTableData();
    }

    private void updateTableData() {
        tableModel.setRowCount(0);
        for (CartItem item : cartItems) {
            FoodItem foodItem = item.getFoodItem();
            tableModel.addRow(new Object[]{
                foodItem.getName(),
                String.format("₹%.2f", foodItem.getPrice()),
                item.getQuantity(),
                String.format("₹%.2f", item.getTotal())
            });
        }
        updateTotal();
    }

    private void updateCart() {
        for (int i = 0; i < cartItems.size(); i++) {
            try {
                int quantity = Integer.parseInt(cartTable.getValueAt(i, 2).toString());
                if (quantity > 0) {
                    cartItems.get(i).setQuantity(quantity);
                } else {
                    cartItems.remove(i);
                    i--;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter valid quantities", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        updateTableData();
    }

    private double calculateTotal() {
        return cartItems.stream()
            .mapToDouble(CartItem::getTotal)
            .sum();
    }

    private void updateTotal() {
        totalLabel.setText(String.format("Total: ₹%.2f", calculateTotal()));
    }
}
