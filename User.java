// Abstract parent class for all users.
public abstract class User implements Authenticatable {
    private int id;
    private String name;
    private String email;
    private String password;

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    protected String getPassword() { return password; }

    @Override
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    // Each role provides its own menu.
    public abstract void displayMenu();
}
