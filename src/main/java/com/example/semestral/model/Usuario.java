package com.example.semestral.model;

import javafx.fxml.FXML;

public class Usuario {

    @FXML
    public int codigo;
    @FXML
    public String usuario;
    @FXML
    public String senha;

    public Usuario(String usuario, String senha) {

        this.senha = senha;
        this.usuario = usuario;

    }
}
