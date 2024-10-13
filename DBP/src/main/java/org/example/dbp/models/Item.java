package org.example.dbp.models;

public class Item {
    private int itemId;
    private String itemName;
    private double price;
    private int inventoryId;
    private int categoryId;

    public Item(){}

    public Item(int itemId , String itemName, double price, int inventoryId, int categoryId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.inventoryId = inventoryId;
        this.categoryId = categoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", inventoryId=" + inventoryId +
                ", categoryId=" + categoryId +
                '}' + "/n";
    }
}
