package com.example.sol.sba;

public class AdminMessage {
    public String user_id, message;

    public AdminMessage(){

    }

    public AdminMessage(String user_id, String message) {
        this.user_id = user_id;
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
