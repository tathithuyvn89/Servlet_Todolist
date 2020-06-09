package model;

public class User {
    private String fistName;
    private String lastName;
    private String username;
    private String password;

    public User() {
    }

    public User(String fistName, String lastName, String username, String password) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
