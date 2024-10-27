package org.example.dbp.models;

public class Inventory {
    private int inventoryId;
    private String inventoryName;
    private String userId;

    public Inventory(){}

    public Inventory(int inventoryId , String inventoryName, String userId){
        this.inventoryId = inventoryId;
        this.inventoryName = inventoryName;
        this.userId = userId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", inventoryName='" + inventoryName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
