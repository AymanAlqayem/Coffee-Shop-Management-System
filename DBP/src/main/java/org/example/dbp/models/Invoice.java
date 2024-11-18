package org.example.dbp.models;

import java.time.LocalDateTime;

public class Invoice {
    private LocalDateTime createdDateTime;
    private double amount;
    private int cashierId;
    private int orderId;

    public Invoice() {
    }

    public Invoice(LocalDateTime createdDateTime, double amount, int cashierId, int orderId) {
        this.createdDateTime = createdDateTime;
        this.amount = amount;
        this.cashierId = cashierId;
        this.orderId = orderId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "createdDateTime=" + createdDateTime +
                ", amount=" + amount +
                ", cashierId=" + cashierId +
                ", orderId=" + orderId +
                '}';
    }
}
