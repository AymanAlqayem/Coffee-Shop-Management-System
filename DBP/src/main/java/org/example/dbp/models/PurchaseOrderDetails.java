package org.example.dbp.models;

import java.util.Date;

import javafx.beans.property.*;

import java.util.Date;

public class PurchaseOrderDetails {
    private int purchaseOrderId;
    private final IntegerProperty vendorId = new SimpleIntegerProperty();
    private double totalPrice;
    private Date orderDate;

    private int lineId;
    private double quantity;
    private double cost_per_unit;

    private int ingredientId;
    private String ingredientName;
    private final ObjectProperty<Unit> unit = new SimpleObjectProperty<>();
    private double ingredientStock;

    public PurchaseOrderDetails(int purchaseOrderId, int vendorId, double totalPrice, Date orderDate, int lineId, double quantity, double cost_per_unit, int ingredientId, String ingredientName, Unit unit, double ingredientStock) {
        this.purchaseOrderId = purchaseOrderId;
        this.vendorId.set(vendorId);
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.lineId = lineId;
        this.quantity = quantity;
        this.cost_per_unit = cost_per_unit;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.unit.set(unit);
        this.ingredientStock = ingredientStock;
    }

    // Getter and Setter for purchaseOrderId
    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(int purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    // JavaFX property for vendorId
    public int getVendorId() {
        return vendorId.get();
    }

    public void setVendorId(int vendorId) {
        this.vendorId.set(vendorId);
    }

    public IntegerProperty vendorIdProperty() {
        return vendorId;
    }

    // Getter and Setter for totalPrice
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Getter and Setter for orderDate
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    // JavaFX property for unit
    public Unit getUnit() {
        return unit.get();
    }

    public void setUnit(Unit unit) {
        this.unit.set(unit);
    }

    public ObjectProperty<Unit> unitProperty() {
        return unit;
    }

    // Getter and Setter for lineId
    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    // Getter and Setter for quantity
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    // Getter and Setter for cost_per_unit
    public double getCost_per_unit() {
        return cost_per_unit;
    }

    public void setCost_per_unit(double cost_per_unit) {
        this.cost_per_unit = cost_per_unit;
    }

    // Getter and Setter for ingredientId
    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    // Getter and Setter for ingredientName
    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    // Getter and Setter for ingredientStock
    public double getIngredientStock() {
        return ingredientStock;
    }

    public void setIngredientStock(double ingredientStock) {
        this.ingredientStock = ingredientStock;
    }
}
