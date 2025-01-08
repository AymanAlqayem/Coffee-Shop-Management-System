package org.example.dbp.models;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

public class PurchaseOrder {
    private int id;
    private Vendor vendor;
    private ObservableList<PurchaseOrderLine> purchaseOrderLine;
    private Double totalPrice;
    private Date orderDate;

    public PurchaseOrder() {
    }

    public PurchaseOrder(int id, Vendor vendor, ObservableList<PurchaseOrderLine> purchaseOrderLine, Double totalPrice, Date orderDate) {
        this.id = id;
        this.vendor = vendor;
        this.purchaseOrderLine = purchaseOrderLine;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public PurchaseOrder(int id, Vendor vendor, ObservableList<PurchaseOrderLine> purchaseOrderLine, Date orderDate) {
        this.id = id;
        this.vendor = vendor;
        this.purchaseOrderLine = purchaseOrderLine;

        // Ensure totalPrice is not null
        if (totalPrice == null) {
            totalPrice = 0.0;
        }

        // Calculate total price
        for (PurchaseOrderLine orderLine : purchaseOrderLine) {
            totalPrice += orderLine.getTotal();
        }

        this.orderDate = orderDate;
    }

    public void setPurchaseOrderLine(ObservableList<PurchaseOrderLine> purchaseOrderLine) {
        this.purchaseOrderLine = purchaseOrderLine;
    }

    public ObservableList<PurchaseOrderLine> getPurchaseOrderLine() {
        return purchaseOrderLine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setCol(String col, Object val) {
        switch (col) {
            case "vendor":
                setVendor((Vendor) val);
                break;
            case "purchaseOrderLine":
                setPurchaseOrderLine((ObservableList<PurchaseOrderLine>) val);
                break;
            case "totalPrice":
                setTotalPrice((Double) val);
                break;
            case "orderDate":
                setOrderDate((Date) val);
                break;
        }
    }
}
