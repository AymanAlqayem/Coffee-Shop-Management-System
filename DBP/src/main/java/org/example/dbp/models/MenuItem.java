package org.example.dbp.models;

public class MenuItem {
    private String itemName;
    private double price;
    private int categoryId;

    public MenuItem(){}

    public MenuItem(String itemName, double price, int categoryId) {
        this.itemName = itemName;
        this.price = price;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "itemName='" + itemName + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                '}';
    }
}
