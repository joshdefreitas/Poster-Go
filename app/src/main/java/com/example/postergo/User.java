package com.example.postergo;

public class User {

    private String user_name;

    public User(String user_name) {
        this.user_name = user_name;
    }

    public String getUsername() {
        return user_name;
    }

    public void setUsername(String user_name) {
        this.user_name = user_name;
    }
}
