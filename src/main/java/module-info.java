module com.example.semestral {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires qrgen;
    requires java.sql;
    requires barbecue;


    opens com.example.semestral to javafx.fxml;
    exports com.example.semestral;
    exports com.example.semestral.controller;
    opens com.example.semestral.controller to javafx.fxml;
    exports com.example.semestral.model;
    opens com.example.semestral.model to javafx.fxml;
}