package org.example.dbp.models;

public class User {
    private String name;
    private String role;
    private String email;
    private String hireDate;
    private String phoneNumber;
    private String pass;
    private String salary;

    public User() {
    }

    public User(String name, String role, String email, String hireDate, String phoneNumber, String pass, String salary) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.hireDate = hireDate;
        this.phoneNumber = phoneNumber;
        this.pass = pass;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHire_date() {
        return hireDate;
    }

    public void setHire_date(String hire_date) {
        this.hireDate = hire_date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", hire_date='" + hireDate + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", pass='" + pass + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
