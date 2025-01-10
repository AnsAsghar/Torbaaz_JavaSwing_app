public class Main {
    public static void main(String[] args) {
        // Create an instance of FileHandler
        FileHandler fileHandler = new FileHandler();
        
        // Start the application with the login form
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginForm(fileHandler);
            }
        });
    }
}
