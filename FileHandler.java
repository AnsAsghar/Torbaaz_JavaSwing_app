import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String USER_FILE = "users.txt";

    public boolean validateLogin(String email, String password) {
        try {
            File file = new File(USER_FILE);
            if (!file.exists()) {
                return false;
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].equals(email) && parts[2].equals(password)) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getNameByEmail(String email) {
        try {
            File file = new File(USER_FILE);
            if (!file.exists()) {
                return null;
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].equals(email)) {
                    scanner.close();
                    return parts[0];
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmailRegistered(String email) {
        try {
            File file = new File(USER_FILE);
            if (!file.exists()) {
                return false;
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].equals(email)) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addUser(String name, String email, String password) {
        if (isEmailRegistered(email)) {
            return false;
        }

        try {
            FileWriter writer = new FileWriter(USER_FILE, true);
            writer.write(name + "," + email + "," + password + "\n");
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        // Basic email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            return false;
        }
        
        // Check for @ and domain
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex == email.length() - 1) {
            return false;
        }
        
        // Check domain has at least one dot and valid ending
        String domain = email.substring(atIndex + 1);
        int lastDotIndex = domain.lastIndexOf('.');
        return lastDotIndex > 0 && lastDotIndex < domain.length() - 2;
    }
}
