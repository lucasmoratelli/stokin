package com.example.semestral.controller;

import com.example.semestral.HelloApplication;
import com.example.semestral.model.Produto;
import javafx.fxml.FXML;

import java.awt.*;

public class QuantidadeModalController {
    @FXML
    TextField quantidadeField;
    @FXML
    public void cancelar() {
        HelloApplication.closeCurrentWindow();
    }

//    @FXML
//    public void salvar() {
//        Produto novoProduto = new Produto();
//        try {
//            novoProduto.quantidade = Integer.parseInt(quantidadeField.getText());
//        }
//    }
}
