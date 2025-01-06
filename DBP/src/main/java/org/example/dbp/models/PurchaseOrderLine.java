package org.example.dbp.models;

import java.util.Date;

public class PurchaseOrderLine {
    private int lineId;
    private int PurchaseOrder;
    private Ingredient ingredient;
    private Double quantity;
    private Double cost_per_unit;
    private Double total;

    public PurchaseOrderLine(int lineId, int purchaseOrder, Ingredient ingredient, Double quantity, Double cost_per_unit, Double total) {
        this.lineId = lineId;
        PurchaseOrder = purchaseOrder;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.cost_per_unit = cost_per_unit;
        this.total = total;
    }

    public PurchaseOrderLine(int lineId, Ingredient ingredient, Double quantity, Double cost_per_unit, Double total) {
        this.lineId = lineId;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.cost_per_unit = cost_per_unit;
        this.total = total;
    }

    public int getPurchaseOrder() {
        return PurchaseOrder;
    }

    public void setPurchaseOrder(int purchaseOrder) {
        PurchaseOrder = purchaseOrder;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getCost_per_unit() {
        return cost_per_unit;
    }

    public void setCost_per_unit(Double cost_per_unit) {
        this.cost_per_unit = cost_per_unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    public void setCol(String col, Object val) {
        switch (col) {


            case "ingredient":
                setIngredient((Ingredient) val);
                break;
            case "quantity":
                setQuantity((Double) val);
                break;
            case "cost_per_unit":
                setCost_per_unit((Double) val);
                break;
            case "total":
                setTotal((Double) val);
                break;


        }
    }

}
