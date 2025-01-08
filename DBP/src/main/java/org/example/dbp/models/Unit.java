package org.example.dbp.models;

public enum Unit {
        KG,L;

    public boolean isKG() {
        return this != L;
    }
    public boolean isL() {
        return this != KG;
    }

}
