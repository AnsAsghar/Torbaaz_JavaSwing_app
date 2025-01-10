import java.io.*;
import java.util.ArrayList;
import java.util.List;


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

	public static List<User> loadUsers() {
		List<User> users = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 4) {
					users.add(new User(parts[0], parts[1], parts[2], parts[3]));
				}
			}
		} catch (IOException e) {
			// If file doesn't exist, create it
			createUsersFile();
		}
		return users;
	}

	public static void saveUser(User user) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE, true))) {
			writer.println(user.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveUsers(List<User> users) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
			for (User user : users) {
				writer.write(user.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createUsersFile() {
		try {
			new File(USERS_FILE).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}