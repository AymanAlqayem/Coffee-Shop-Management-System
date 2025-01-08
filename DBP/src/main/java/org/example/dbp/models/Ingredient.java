package org.example.dbp.models;

public class Ingredient {
    private int ingredientId;
    private String ingredientName;
    private Unit unit;
    private double quantity;


    public Ingredient() {

    }

    public Ingredient(String ingredientName, Unit unit, double quantity) {
        this.ingredientName = ingredientName;
        this.unit = unit;
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Ingredient(int ingredientId, String ingredientName, Unit unit, double quantity) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.unit = unit;
        this.quantity = quantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setInventoryId(int inventoryId) {
        this.ingredientId = inventoryId;
    }

    @Override
    public String toString() {
        return "name=" + ingredientName +
                " Unit=" + unit +
                ", quantity=" + quantity
                ;
    }

    public void setCol(String col, Object val) {
        switch (col) {
            case "ingredientName":
                setIngredientName((String) val);
                break;
            case "unit":
                setUnit((Unit) val);
                break;
            case "quantity":
                setQuantity((Double) val);
                break;
        }
    }
}
