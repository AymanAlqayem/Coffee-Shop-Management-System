package org.example.dbp.models;

import java.util.ArrayList;

public class Category {
    private int categoryId;
    private String categoryName;
    ArrayList<Item> items = new ArrayList<Item>();


    public Category() {
    }

    public Category(int categoryId , String categoryName , ArrayList<Item> items) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.items = items;
    }
    public Category( String categoryName , ArrayList<Item> items) {
        this.categoryName = categoryName;
        this.items = items;
    }
    public Category( String categoryName) {
        this.categoryName = categoryName;
        items = new ArrayList<>();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n').append("Category{")
                .append("categoryId=").append(categoryId)
                .append(", categoryName='").append(categoryName).append('\'')
                .append(", items=\n");

        for (Item item : items) {
            sb.append(item).append("\n"); // Assuming Item class has a proper toString() method

        }
        sb.append('}');
        sb.append('\n');
        sb.append("---------------------------------------------------------------");
        return sb.toString();
    }

}
