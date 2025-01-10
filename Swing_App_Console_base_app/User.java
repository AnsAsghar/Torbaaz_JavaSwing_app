public class User {
	private String email;
	private String password;
	private String fullName;

	public User(String email, String password, String fullName) {
		this.email = email;
		this.password = password;
		this.fullName = fullName;
	}

	// Getters and setters
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }

	// Validation methods
	public static boolean isValidEmail(String email) {
		// Simple regex for email validation
		return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	}

	public static boolean isValidPassword(String password) {
		// Password must be at least 4 characters
		return password != null && password.length() >= 4;
	}

	public static boolean isValidFullName(String fullName) {
		// Full name must be at least 2 characters
		return fullName != null && fullName.length() >= 2;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s", email, password, fullName);
	}

	public boolean authenticate(String email, String password) {
		return this.email.equals(email) && this.password.equals(password);
	}
}