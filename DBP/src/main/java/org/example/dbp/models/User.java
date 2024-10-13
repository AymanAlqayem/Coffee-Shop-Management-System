package org.example.dbp.models;

public class User {
    private String userName;
    private String role;
    private String hire_date;
    private String email;
    private String salary;
    private String pass;


    public User() {

    }

    public User(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
//        this.role = role;
    }

    public User(String userName, String role, String hire_date, String email, String salary, String pass) {
        this.userName = userName;
        this.role = role;
        this.hire_date = hire_date;
        this.email = email;
        this.salary = salary;
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHire_date() {
        return hire_date;
    }

    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
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
                ", role='" + role + '\'' +
                ", hire_date='" + hire_date + '\'' +
                ", email='" + email + '\'' +
                ", salary='" + salary + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
