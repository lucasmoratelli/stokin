package com.example.semestral.controller;

import com.example.semestral.HelloApplication;
import com.example.semestral.model.Notificacao;
import com.example.semestral.model.NotificacaoDAO;
import com.example.semestral.model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
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
    public void salvar() throws SQLException {
        produto.quantidade = Integer.parseInt(quantidadeNoEstoqueField.getText());
//        Produto novoProduto = new Produto();
//        novoProduto.quantidade = Integer.parseInt(quantidadeNoEstoqueField.getText());
//        novoProduto.produtoID = produto.produtoID;
//        novoProduto.nomeProduto = produto.nomeProduto;
//        novoProduto.marca = produto.marca;
//        novoProduto.descricao = produto.descricao;
//        novoProduto.unidadeMedida = produto.unidadeMedida;
//        novoProduto.fornecedorID = produto.fornecedorID;
//        novoProduto.quantidadeMinima = produto.quantidadeMinima;
//        novoProduto.preco = produto.preco;
        if(produto.quantidade < produto.quantidadeMinima) {
            Notificacao novaNotificacao = new Notificacao();
            NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
            novaNotificacao.produtoID = produto.produtoID;
            novaNotificacao.notificaTexto = ("O Produto " + produto.nomeProduto + " está em fim de estoque!");
            notificacaoDAO.insert(novaNotificacao);
        }
        HelloApplication.closeCurrentWindow();
    }
    @FXML
    public void entrada() {
        try {
            int resultadoEstoque;
            int qtdEstoque = Integer.parseInt(quantidadeNoEstoqueField.getText());
            int qtdEntrada = Integer.parseInt(quantidadeField.getText());
            resultadoEstoque = qtdEstoque + qtdEntrada;
            quantidadeNoEstoqueField.setText(Integer.toString(resultadoEstoque));
        }catch (Exception e) {
            labelCheckYourFields.setText("Insira algum número!");
        }

    }
    @FXML
    public void saida() {
        try {
            int resultadoEstoque;
            int qtdEstoque = Integer.parseInt(quantidadeNoEstoqueField.getText());
            int qtdEntrada = Integer.parseInt(quantidadeField.getText());
            resultadoEstoque = qtdEstoque - qtdEntrada;
            quantidadeNoEstoqueField.setText(Integer.toString(resultadoEstoque));
        } catch (Exception e) {
            labelCheckYourFields.setText("Insira algum número!");

        }


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
