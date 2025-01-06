package org.example.dbp.models;

public class Vendor {
    private int venderId;
    private Address address;
    private String vendorName;


    public Vendor(int venderId, String vendorName, Address address) {
        this.venderId = venderId;
        this.vendorName = vendorName;
        this.address = address;
    }

    public int getVenderId() {
        return venderId;
    }

    public void setVenderId(int venderId) {
        this.venderId = venderId;
    }



    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }


    @Override
    public String toString() {
        return "vendorName= " + vendorName + ", address= " + address.getCity() + "," + address .getStreet();
    }
}
