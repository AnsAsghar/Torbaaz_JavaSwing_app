public class FoodItem {
    private String name;
    private String description;
    private double price;
    private String category;
    private String imagePath;

    public FoodItem(String name, String description, double price, String category) {
        this(name, description, price, category, null);
    }

    public FoodItem(String name, String description, double price, String category, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath != null ? imagePath : getDefaultImagePath(category);
    }

    private String getDefaultImagePath(String category) {
        switch(category.toLowerCase()) {
            case "pizza": return "Images/pizza.jpg";
            case "burger": return "Images/burger.jpg";
            case "pasta": return "Images/alfredopasta.jpg";
            case "appetizers": return "Images/garlicbread.jpg";
            default: return "Images/default.jpg";
        }
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getImagePath() { return imagePath; }
}
