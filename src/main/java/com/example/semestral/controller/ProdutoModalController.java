package com.example.semestral.controller;

import com.example.semestral.HelloApplication;
import com.example.semestral.model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ProdutoModalController implements Initializable {

    public static Produto produto;

    @FXML
    TextField produtoIDField;

    @FXML
    TextField fornecedorIDField;

    @FXML
    TextField nomeProdutoField;

    @FXML
    TextField marcaField;

    @FXML
    TextArea descricaoField;

    @FXML
    TextField quantidadeField;

    @FXML
    TextField unidadeMedidaField;

    @FXML
    TextField precoField;

    @FXML
    TextField quantidadeMinimaField;

    @FXML
    Label labelCheckYourFields;

    @FXML
    Label labelQtd;

    @FXML
    public void salvar() {

        Produto novoProduto = new Produto();
        try {
            if(!produtoIDField.getText().isBlank()) {
                novoProduto.produtoID = Integer.parseInt(produtoIDField.getText());
            }
            novoProduto.nomeProduto = nomeProdutoField.getText();
            novoProduto.marca = marcaField.getText();
            novoProduto.descricao = descricaoField.getText();
            novoProduto.unidadeMedida = unidadeMedidaField.getText();
            novoProduto.fornecedorID = Integer.parseInt(fornecedorIDField.getText());
            novoProduto.quantidade = Integer.parseInt(quantidadeField.getText());
            novoProduto.quantidadeMinima = Integer.parseInt(quantidadeMinimaField.getText());
            if(precoField.getText().matches("\\d+\\.?\\d+")) {
                novoProduto.preco = Double.parseDouble(precoField.getText());
                produto = novoProduto;
                HelloApplication.closeCurrentWindow();
            }else {
                labelCheckYourFields.setText("O preço deve ser no formato '999999.99'");
            }

        }catch (Exception e) {
           labelCheckYourFields.setText("Todos os campos são obrigatórios");
           System.out.println(e);
        }
    }
    @FXML
    public void cancelar() {
        HelloApplication.closeCurrentWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Produto produtoSelecionado = ProdutoModalController.produto;

        if (produto != null) {
            produtoIDField.setText(Integer.toString(produtoSelecionado.produtoID));
            fornecedorIDField.setText(Integer.toString(produtoSelecionado.fornecedorID));
            nomeProdutoField.setText(produtoSelecionado.nomeProduto);
            marcaField.setText(produtoSelecionado.marca);
            descricaoField.setText(produtoSelecionado.descricao);
            quantidadeField.setText(Integer.toString(produtoSelecionado.quantidade));
            unidadeMedidaField.setText(produtoSelecionado.unidadeMedida);
            precoField.setText(Double.toString(produtoSelecionado.preco));
            quantidadeMinimaField.setText(Integer.toString(produtoSelecionado.quantidadeMinima));
        }
        precoField.textProperty().addListener((o, oldValue, newValue) -> {
            precoField.setText(newValue.replaceAll("[^\\d.]", ""));
        });
        quantidadeMinimaField.textProperty().addListener((o, oldValue, newValue) -> {
            quantidadeMinimaField.setText(newValue.replaceAll("[^\\d.]", ""));
        });
        quantidadeField.textProperty().addListener((o, oldValue, newValue) -> {
            quantidadeField.setText(newValue.replaceAll("[^\\d.]", ""));
        });
        precoField.textProperty().addListener((o, oldValue, newValue) -> {
            precoField.setText(newValue.replaceAll("[^\\d.]", ""));
        });

        quantidadeField.setDisable(ProdutoController.disableQtd);
        labelQtd.setDisable(ProdutoController.disableQtd);
    }
}

