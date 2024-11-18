package org.example.dbp.models;

public class Customer {
    private String customerName;
    private String customerPhone;

    public Customer() {
    }

    public Customer(String customerName, String customerAddress, String customerPhone) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                '}';
    }
}
