package com.example.semestral.controller;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;



public class ConfigModalController {
    public static File barcodeDirectory;
    @FXML
    public void barcodeConfig() {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        barcodeDirectory = directoryChooser.showDialog(stage);
    }

}
