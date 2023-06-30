package com.example.semestral.controller;

import com.example.semestral.HelloApplication;
import com.example.semestral.model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.semestral.controller.ProdutoModalController.produto;

public class QuantidadeModalController implements Initializable {
    @FXML
    TextField quantidadeNoEstoqueField;
    @FXML
    TextField quantidadeField;
    @FXML
    Label labelCheckYourFields;
    @FXML
    public void cancelar() {
        HelloApplication.closeCurrentWindow();
    }

    @FXML
    public void salvar() {
        Produto novoProduto = new Produto();
        novoProduto.quantidade = Integer.parseInt(quantidadeNoEstoqueField.getText());
        produto = novoProduto;
        HelloApplication.closeCurrentWindow();
    }
    @FXML
    public void entrada() {
        int resultadoEstoque;
        int qtdEstoque = Integer.parseInt(quantidadeNoEstoqueField.getText());
        int qtdEntrada = Integer.parseInt(quantidadeField.getText());
        resultadoEstoque = qtdEstoque + qtdEntrada;
        quantidadeNoEstoqueField.setText(Integer.toString(resultadoEstoque));

    }
    @FXML
    public void saida() {
        int resultadoEstoque;
        int qtdEstoque = Integer.parseInt(quantidadeNoEstoqueField.getText());
        int qtdEntrada = Integer.parseInt(quantidadeField.getText());
        resultadoEstoque = qtdEstoque - qtdEntrada;
        quantidadeNoEstoqueField.setText(Integer.toString(resultadoEstoque));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Produto produtoSelecionado = produto;

        if (produto != null) {
            quantidadeNoEstoqueField.setText(Integer.toString(produtoSelecionado.quantidade));
        }
        quantidadeField.textProperty().addListener((o, oldValue, newValue) -> {
            quantidadeField.setText(newValue.replaceAll("[^\\d.]", ""));
        });
    }
}
