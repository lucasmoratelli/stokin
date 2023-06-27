package com.example.semestral.controller;

import com.example.semestral.AcessLevelSingleton;
import com.example.semestral.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Separator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    Button forncecedor;

    @FXML
    Button compras;

    @FXML
    public void estoque() throws IOException {
        HelloApplication.setRoot("produtos-view");
    }

    @FXML
    public void fornecedores() throws IOException {
        HelloApplication.setRoot("fornecedores-view");
    }

    @FXML
    public void compras() throws IOException {
        HelloApplication.setRoot("compras-view");
    }

    @FXML
    public void sair() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sair?");
        alert.setHeaderText("Você está prestes a realizar logout!");
        alert.setContentText("Você realmente deseja realizar logout?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            AcessLevelSingleton.acessLevel = 0;
            HelloApplication.setRoot("login-view");
        }
    }
    @FXML
    public void abrirNotificacoes() throws IOException {
        HelloApplication.showModal("notificacao-modal-view");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(AcessLevelSingleton.canAcess()) {
            forncecedor.setVisible(false);
            compras.setVisible(false);
        }
    }
}
