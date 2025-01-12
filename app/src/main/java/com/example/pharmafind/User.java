package com.example.pharmafind;

public class User {
    private String userUid;
    private String username;
    private String email;

    public User() {
        // Required default constructor for Firebase
    }

    public User(String userUid, String username, String email) {
        this.userUid = userUid;
        this.username = username;
        this.email = email;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
