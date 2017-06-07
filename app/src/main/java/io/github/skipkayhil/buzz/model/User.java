package io.github.skipkayhil.buzz.model;

public class User {
    private static final User instance = new User();
    public static User getInstance() { return instance; }

    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
