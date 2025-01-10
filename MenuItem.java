public class MenuItem {
	private int id;
	private String name;
	private double price;
	private String category;
	private String description;
	private boolean availability;

	public MenuItem(int id, String name, double price, String category, String description, boolean availability) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
		this.availability = availability;
	}

	// Getters and setters
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }
	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public boolean isAvailable() { return availability; }
	public void setAvailability(boolean availability) { this.availability = availability; }

	@Override
	public String toString() {
		return String.format("%d,%s,%.2f,%s,%s,%b", id, name, price, category, description, availability);
	}

	public static void main(String[] args) {
		MenuItem item = new MenuItem(1, "Pizza", 9.99, "Main Course", "Delicious cheese pizza", true);
		System.out.println(item);
	}
}