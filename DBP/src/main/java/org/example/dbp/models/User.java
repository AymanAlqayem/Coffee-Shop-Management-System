package org.example.dbp.models;

public class User {
    private String userName;
    private String pass;
//    private String role;

    public User() {

    }

    public User(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
//        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
