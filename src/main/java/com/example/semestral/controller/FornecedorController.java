package com.example.semestral.controller;

import com.example.semestral.HelloApplication;
import com.example.semestral.model.*;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.semestral.controller.FornecedorModalController.fornecedor;

public class FornecedorController implements Initializable {

    @FXML
    TableView<Fornecedor> tabelaFornecedores;

    @FXML
    TableColumn<Fornecedor, Integer> colunaFornecedorID;

    @FXML
    TableColumn<Fornecedor, String> colunaNomeFornecedor;

    @FXML
    TableColumn<Fornecedor, String> colunaCNPJ;

    @FXML
    TableColumn<Fornecedor, String> colunaTelefone;

    @FXML
    TableColumn<Fornecedor, String> colunaSite;

    @FXML
    TableColumn<Fornecedor, String> colunaPais;

    @FXML
    Button editar;

    @FXML
    Button excluir;

    @FXML
    Button novo;

    //Essa parte é executada ao iniciar a classe
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colunaFornecedorID.setCellValueFactory(new PropertyValueFactory<>("fornecedorID"));
        colunaNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedor"));
        colunaCNPJ.setCellValueFactory(new PropertyValueFactory<>("CNPJ"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaSite.setCellValueFactory(new PropertyValueFactory<>("site"));
        colunaPais.setCellValueFactory(new PropertyValueFactory<>("pais"));

        //Pega os itens armazenados no BD e coloca na tabela
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        try {
            List<Fornecedor> fornecedores = fornecedorDAO.getAll();
            tabelaFornecedores.getItems().addAll(fornecedores);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //método que desabilita botões excluir e editar enquanto nenhum produto da tabela estiver selecionado
    @FXML
    public void habilitarBotoes() {
        BooleanBinding algoSelecionado = tabelaFornecedores.getSelectionModel().selectedItemProperty().isNull();
        excluir.disableProperty().bind(algoSelecionado);
        editar.disableProperty().bind(algoSelecionado);
    }

    //método para adicionar novo produto na tabela
    @FXML
    public void novo() throws IOException, SQLException {
        fornecedor = null; //define variável global 'produto' como nula para prevenção de bugs
        HelloApplication.showModal("fornecedor-modal-view"); //abre o modal de cadastro de produto

        //adiciona o novo produto na tabela e no BD se a variavel global for diferente de nula e se o fornecedor descrito existir no BD
        if(fornecedor != null) {
            FornecedorDAO fornecedorDAO = new FornecedorDAO();
            tabelaFornecedores.getItems().add(fornecedor);
            fornecedorDAO.insert(fornecedor);
        }
    }

    //método para voltar para a tela anterior
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }

    //método para editar produto
    @FXML
    public void editar() throws IOException, SQLException {
        fornecedor = null; //define variável global 'produto' como nula para prevenção de bugs
        Fornecedor fornecedorSelecionado = tabelaFornecedores.getSelectionModel().getSelectedItem();
        fornecedor = fornecedorSelecionado; //coloca essa variável dentro da váriável global produto
        HelloApplication.showModal("fornecedor-modal-view"); //abre o modal de edição de produto

        //edita o fornecedor na tabela e no BD
        fornecedorSelecionado.fornecedorID = fornecedor.fornecedorID;
        fornecedorSelecionado.nomeFornecedor = fornecedor.nomeFornecedor;
        fornecedorSelecionado.CNPJ = fornecedor.CNPJ;
        fornecedorSelecionado.telefone = fornecedor.telefone;
        fornecedorSelecionado.site = fornecedor.site;
        fornecedorSelecionado.pais = fornecedor.pais;
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        fornecedorDAO.update(fornecedor);
        tabelaFornecedores.refresh();
    }

    //método que exclui o item selecionado da tabela e BD
    @FXML
    public void excluir() throws SQLException {
        Fornecedor fornecedorSelecionado = tabelaFornecedores.getSelectionModel().getSelectedItem(); //define uma váriavel Produto para inserir o item selecionado
        //gera uma confirmação de exclusão
        Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION);
        alertDelete.setTitle("Confirmação de Exclusão");
        alertDelete.setHeaderText(fornecedorSelecionado.nomeFornecedor);
        alertDelete.setContentText("Deseja excluir esse fornecedor?");
        Optional<ButtonType> result = alertDelete.showAndWait();
        //se o usuário clicar em ok, remove o produto selecionado da tabela e BD
        if (result.get() == ButtonType.OK) {
            try {
                FornecedorDAO fornecedorDAO = new FornecedorDAO();
                fornecedorDAO.delete(fornecedorSelecionado);
                tabelaFornecedores.getItems().remove(fornecedorSelecionado);
            } catch (Exception e) {
                Alert alertError = new Alert(Alert.AlertType.CONFIRMATION);
                alertError.setHeaderText("Você não pode excluir esse fornecedor!");
                alertError.setContentText("Ainda existem produtos desse fornecedor cadastrados");
                alertError.showAndWait();
            }
        }
    }
}
