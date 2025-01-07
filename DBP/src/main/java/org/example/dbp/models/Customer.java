package org.example.dbp.models;


public class Customer {
    private int id;
    private String customerName;
    private Long customerPhone;

    public Customer() {
    }

    public Customer(String customerName, Long customerPhone) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public Customer(int id, String customerName, Long customerPhone) {
        this.id = id;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(Long customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                "customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                '}';
    }

    public void setCol(String col, Object val) {
        switch (col) {
            case "customerName":
                setCustomerName(String.valueOf((val.toString())));
                break;
            case "phoneNumber":
                setCustomerPhone(Long.valueOf((val.toString())));
                break;
        }
    }
}
