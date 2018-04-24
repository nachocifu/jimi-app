package edu.itba.paw.jimi.models;

public class User {


    private long id;
    private String name;

    public User(String name, long id) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
