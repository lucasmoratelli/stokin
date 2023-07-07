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
    public static File qrCodeDirectory;
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
    }

    @FXML
    public void QRCodeConfig() throws SQLException {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        qrCodeDirectory = directoryChooser.showDialog(stage);
        String qrCodeParameter = String.valueOf(qrCodeDirectory);
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.configQRCode(qrCodeParameter);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigDAO configDAO = new ConfigDAO();
        try {
            List<String> configs = configDAO.getConfigs();
            ConfigModalController.barcodeDirectory = new File(configs.get(0));
//            ConfigModalController.qrCodeDirectory = new File(configs.get(1));
        } catch (SQLException e) {
            System.out.println(e);
        }
        labelBarcode.setText(String.valueOf(barcodeDirectory));
    }
}
