package com.commercial.commerce.chat.model;

public class User {

    String id;
    String username;
    String picturePath;

    public User(String id, String username, String picturePath) {
        this.id = id;
        this.username = username;
        this.picturePath = picturePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

}
