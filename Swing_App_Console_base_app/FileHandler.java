import java.io.*;
import java.util.*;
import java.util.List; // Added missing import

public class FileHandler {
	private static final String MENU_FILE = "dataset.txt";
	private static final String USERS_FILE = "users.txt";

	public static List<MenuItem> loadMenuItems() {
		List<MenuItem> menuItems = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty() || line.startsWith("-")) continue;
				String[] parts = line.split(" - ");
				if (parts.length >= 2) {
					String name = parts[0].trim();
					String priceStr = parts[1].trim().replaceAll("[^0-9.]", "");
					try {
						double price = Double.parseDouble(priceStr);
						menuItems.add(new MenuItem(
							menuItems.size() + 1,
							name,
							price,
							"General",  // Default category
							"",         // Default description
							true       // Default availability
						));
					} catch (NumberFormatException e) {
						System.err.println("Error parsing price for item: " + name);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return menuItems;
	}

	public static User validateUser(String email, String password) {
		List<User> users = loadUsers(); 
		for (User user : users) {
			if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null; 
	}

	public static boolean emailExists(String email) {
		List<User> users = loadUsers();
		for (User user : users) {
			if (user.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}
		return false;
	}

	public static List<User> loadUsers() {
		List<User> users = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 3) {
					users.add(new User(parts[0], parts[1], parts[2]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

	public static void saveUsers(List<User> users) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE, false))) {
			for (User user : users) {
				pw.println(user.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveUser(User user) {
		List<User> users = loadUsers();
		users.add(user);
		saveUsers(users);
	}

	public static void saveUser(User user) {
		List<User> users = loadUsers();
		boolean userExists = false;

		// Check if user exists and update
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getEmail().equals(user.getEmail())) {
				users.set(i, user);
				userExists = true;
				break;
			}
		}

		// Add new user if doesn't exist
		if (!userExists) {
			users.add(user);
		}

		// Save updated user list
		saveUsers(users);
	}

	public static boolean authenticateUser(String email, String password) {
		if (email == null || password == null) {
			return false;
		}

		// Check users from the file
		List<User> users = loadUsers();
		for (User user : users) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	public static User getUserByEmail(String email) {
		List<User> users = loadUsers();
		for (User user : users) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}

	private static void createUsersFile() {
		try {
			File file = new File(USERS_FILE);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			System.err.println("Error creating users file: " + e.getMessage());
		}
	}
}