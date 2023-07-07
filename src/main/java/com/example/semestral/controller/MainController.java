package com.example.semestral.controller;

import com.example.semestral.AcessLevelSingleton;
import com.example.semestral.HelloApplication;
import com.example.semestral.model.ConfigDAO;
import com.example.semestral.model.Notificacao;
import com.example.semestral.model.NotificacaoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    Button forncecedor;

    @FXML
    Button compras;

    @FXML
    Button notificacao;

    @FXML
    Button config;

    @FXML
    Label notificando1;

    @FXML
    Label notificando2;

    @FXML
    public void estoque() throws IOException {
        HelloApplication.setRoot("produtos-view");
    }

    @FXML
    public void fornecedores() throws IOException {
        HelloApplication.setRoot("fornecedores-view");
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
        try {
            NotificacaoDAO notificacaoDAO = new NotificacaoDAO();

            List<Notificacao> notificacoes = notificacaoDAO.getAll();
            if (notificacoes.isEmpty()) {
                notificando1.setVisible(false);
                notificando2.setVisible(false);
            } else {
                notificando1.setVisible(true);
                notificando2.setVisible(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void config() throws IOException {
        HelloApplication.showModal("config-modal-view");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(AcessLevelSingleton.canAcess()) {
            forncecedor.setVisible(false);
            compras.setVisible(false);
            notificacao.setVisible(false);
            config.setVisible(false);
        }
        try {
            NotificacaoDAO notificacaoDAO = new NotificacaoDAO();

            List<Notificacao> notificacoes = notificacaoDAO.getAll();
            if (notificacoes.isEmpty()) {
                notificando1.setVisible(false);
                notificando2.setVisible(false);
            } else {
                notificando1.setVisible(true);
                notificando2.setVisible(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
