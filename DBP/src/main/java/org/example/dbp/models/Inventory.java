package org.example.dbp.models;

public class Inventory {
    private String name;
    private int adminId;

    public Inventory() {

    }

    public Inventory(String name, int adminId) {
        this.name = name;
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "name='" + name + '\'' +
                ", adminId=" + adminId +
                '}';
    }
}
