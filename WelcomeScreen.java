import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen(String username) {
        new MenuPage(username);
        dispose(); // Close the welcome screen
    }
}
