package org.example.dbp.models;

import java.time.LocalDate;

public class InventoryItem {
    private int name;
    private double quantity;
    private LocalDate productionDate;
    private LocalDate expiryDate;
    private int inventoryId;

    public InventoryItem() {

    }

    public InventoryItem(int name, double quantity, LocalDate productionDate, LocalDate expiryDate, int inventoryId) {
        this.name = name;
        this.quantity = quantity;
        this.productionDate = productionDate;
        this.expiryDate = expiryDate;
        this.inventoryId = inventoryId;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "name=" + name +
                ", quantity=" + quantity +
                ", productionDate=" + productionDate +
                ", expiryDate=" + expiryDate +
                ", inventoryId=" + inventoryId +
                '}';
    }
}
