package org.example.dbp.models;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String role;
    private String email;
    private Date hireDate;
    private long phoneNumber;
    private String pass;
    private double salary;

    public User() {
    }

    public User(String name, String role, String email, Date hireDate, long phoneNumber, String pass, double salary) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.hireDate = hireDate;
        this.phoneNumber = phoneNumber;
        this.pass = pass;
        this.salary = salary;
    }

    public User(int id, String name, String role, String email, Date hireDate, long phoneNumber, String pass, double salary) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.hireDate = hireDate;
        this.phoneNumber = phoneNumber;
        this.pass = pass;
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getSalary() {
        return salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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



    public void setHire_date(Date hire_date) {
        this.hireDate = hire_date;
    }



    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }



    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", hire_date='" + hireDate + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", pass='" + pass + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }

    public void setCol(String col, Object val) {
        switch (col) {
            case "name":
                setName(String.valueOf((val.toString())));
                break;
            case "role":
                setRole(val.toString());
                break;
            case "email":
                setEmail(String.valueOf((val.toString())));
                break;
            case "hireDate":
                setHire_date((Date) val);
                break;
            case "phoneNumber":
                setPhoneNumber(Integer.parseInt(val.toString()));
                break;
            case "pass":
                setPass(val.toString());
                break;
            case "salary":
                setSalary((Double) val);
                break;

        }
    }
}
