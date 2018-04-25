package edu.itba.paw.jimi.models;

public class User {


    private long id;
    private String username;
    private String password;

    public User(String name, long id) {
        this.id = id;
        this.username = name;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
