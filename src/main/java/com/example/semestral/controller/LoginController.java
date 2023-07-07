package com.example.semestral.controller;

import com.example.semestral.AcessLevelSingleton;
import com.example.semestral.HelloApplication;
import com.example.semestral.model.Usuario;
import com.example.semestral.model.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    TextField usuarioField;
    @FXML
    PasswordField senhaField;
    @FXML
    Label checkPasswordLabel;
    @FXML
    Button buttomEntrar;
    @FXML
    public void entrar() {

        try {
            Usuario usuarioLogin = new Usuario(usuarioField.getText(), senhaField.getText());
            boolean usuarioExiste = new UsuarioDAO().existe(usuarioLogin);
            AcessLevelSingleton.acessLevel = new UsuarioDAO().acessLevel(usuarioLogin);

            if (usuarioExiste) {

                System.out.println("Entrando...");
                checkPasswordLabel.setText("Entrando...");
                HelloApplication.setRoot("main-view");
                AcessLevelSingleton.acessLevel = new UsuarioDAO().acessLevel(usuarioLogin);

            }
            else {

                System.out.println("Usuário e/ou Senha Incorretos!");
                checkPasswordLabel.setText("Usuário e/ou Senha Incorretos!");

            }
        } catch (Exception e) {
            checkPasswordLabel.setText("Usuário e/ou Senha Incorretos!");
        }
    }
    public void esqueceuSenha() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajuda");
        alert.setHeaderText(null);
        alert.setContentText("Entre em contato com o departamento de TI de sua \nempresa ou envie um email para suporte@stokin.com.br");
        alert.showAndWait();

    }
    public void desabilitaLogin() {

        boolean loginFields;
        loginFields = usuarioField.getText().isEmpty() | senhaField.getText().isEmpty();
        buttomEntrar.setDisable(loginFields);

    }
}