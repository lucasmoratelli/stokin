package com.example.semestral.controller;

import com.example.semestral.model.Notificacao;
import com.example.semestral.model.NotificacaoDAO;
import com.example.semestral.model.Produto;
import com.example.semestral.model.ProdutoDAO;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class NotificacaoModalController  implements Initializable{
    @FXML
    TableView <Notificacao> tabelaNotificacao;
    @FXML
    TableColumn<Notificacao, Integer> colunaCodigoProduto;
    @FXML
    TableColumn<Notificacao, String> colunaNotificacao;
    @FXML
    TableColumn<Notificacao, String> colunaCodigoNotifica;
    @FXML
    Button excluir;

    public void initialize (URL url, ResourceBundle resourceBundle) {
        colunaCodigoNotifica.setCellValueFactory(new PropertyValueFactory<>("notificacaoID"));
        colunaCodigoProduto.setCellValueFactory(new PropertyValueFactory<>("produtoID"));
        colunaNotificacao.setCellValueFactory(new PropertyValueFactory<>("notificaTexto"));
        NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
        try {
            List<Notificacao> notificacoes = notificacaoDAO.getAll();
            tabelaNotificacao.getItems().addAll(notificacoes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void excluir() throws SQLException {
        Notificacao notificacaoSelecionada = tabelaNotificacao.getSelectionModel().getSelectedItem(); //define uma váriavel Produto para inserir o item selecionado
        //gera uma confirmação de exclusão
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setContentText("Esse produto já foi comprado para preencher o estoque?");
        Optional<ButtonType> result = alert.showAndWait();
        //se o usuário clicar em ok, remove o produto selecionado da tabela e BD
        if (result.get() == ButtonType.OK) {
            tabelaNotificacao.getItems().remove(notificacaoSelecionada);
            NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
            notificacaoDAO.delete(notificacaoSelecionada);
        }
    }
    @FXML
    public void habilitarBotoes() {
        BooleanBinding algoSelecionado = tabelaNotificacao.getSelectionModel().selectedItemProperty().isNull();
        excluir.disableProperty().bind(algoSelecionado);
    }
}
