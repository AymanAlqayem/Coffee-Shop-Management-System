module org.example.dbp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.dbp to javafx.fxml;
    exports org.example.dbp;
}