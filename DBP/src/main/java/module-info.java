module org.example.dbp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires com.jfoenix;
    requires jdk.jdi;
    requires java.desktop;

    opens org.example.dbp to javafx.fxml;
    exports org.example.dbp.controllers;
    opens org.example.dbp.controllers to javafx.fxml;
    exports org.example.dbp.models;
    opens org.example.dbp.models to javafx.fxml;
}