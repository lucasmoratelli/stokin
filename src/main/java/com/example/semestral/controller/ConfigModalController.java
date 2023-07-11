package com.example.semestral.controller;

import com.example.semestral.model.ConfigDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class ConfigModalController implements Initializable {
    public static File barcodeDirectory;
    @FXML
    Label labelQRCode;
    @FXML
    Label labelBarcode;
    @FXML
    public void barcodeConfig() throws SQLException {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        barcodeDirectory = directoryChooser.showDialog(stage);
        String barcodeParameter = String.valueOf(barcodeDirectory);
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.configBarcode(barcodeParameter);
        labelBarcode.setText(barcodeParameter);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigDAO configDAO = new ConfigDAO();

        List<String> configs;
        try {
            configs = configDAO.getConfigs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConfigModalController.barcodeDirectory = new File(configs.get(0));

        labelBarcode.setText(String.valueOf(barcodeDirectory));
    }
}
