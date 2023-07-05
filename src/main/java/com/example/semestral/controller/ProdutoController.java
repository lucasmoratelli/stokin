package com.example.semestral.controller;

import com.example.semestral.AcessLevelSingleton;
import com.example.semestral.HelloApplication;
import com.example.semestral.model.Produto;
import com.example.semestral.model.ProdutoDAO;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;


import static com.example.semestral.controller.ProdutoModalController.produto;

public class ProdutoController implements Initializable {

    @FXML
    TableView<Produto> tabelaProdutos;

    @FXML
    TableColumn<Produto, Integer> colunaProdutoID;

    @FXML
    TableColumn<Produto, Integer> colunaFornecedorID;

    @FXML
    TableColumn<Produto, String> colunaNomeProduto;

    @FXML
    TableColumn<Produto, String> colunaMarca;

    @FXML
    TableColumn<Produto, String> colunaDescricao;

    @FXML
    TableColumn<Produto, Integer> colunaQuantidade;

    @FXML
    TableColumn<Produto, String> colunaUnidadeMedida;

    @FXML
    TableColumn<Produto, Double> colunaPreco;

    @FXML
    TableColumn<Produto, Integer> colunaQuantidadeMinima;

    @FXML
    Button editar;

    @FXML
    Button excuir;

    @FXML
    Button novo;

    @FXML
    Button entraSaiButtom;

    //Essa parte é executada ao iniciar a classe
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //implementação dos níveis de acesso, impossibilitando um usuário da Produção de editar ou excluir itens da lista
        editar.setVisible(!AcessLevelSingleton.canAcess());
        excuir.setVisible(!AcessLevelSingleton.canAcess());
        novo.setVisible(!AcessLevelSingleton.canAcess());

        colunaProdutoID.setCellValueFactory(new PropertyValueFactory<>("produtoID"));
        colunaFornecedorID.setCellValueFactory(new PropertyValueFactory<>("fornecedorID"));
        colunaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaUnidadeMedida.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaQuantidadeMinima.setCellValueFactory(new PropertyValueFactory<>("quantidadeMinima"));

        //Pega os itens armazenados no BD e coloca eles na tabela
        ProdutoDAO produtoDAO = new ProdutoDAO();
        try {
            List<Produto> produtos = produtoDAO.getAll();
            tabelaProdutos.getItems().addAll(produtos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    //método para adicionar novo produto na tabela
    @FXML
    public void novo() throws IOException, SQLException, OutputException, BarcodeException {
        disableQtd = false;
        produto = null; //define variável global 'produto' como nula para prevenção de bugs
        HelloApplication.showModal("produto-modal-view"); //abre o modal de cadastro de produto

        //adiciona o novo produto na tabela e no BD se a variavel global for diferente de nula e se o fornecedor descrito existir no BD
        if(produto != null) {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            if(produtoDAO.checkFornecedor(produto.fornecedorID)) {
                tabelaProdutos.getItems().add(produto);
                produtoDAO.insert(produto);
                Barcode bc = BarcodeFactory.createCode128(String.valueOf(produto.produtoID));
                bc.setBarHeight(60);
                bc.setBarWidth(2);

                //cria o arquivo
                File file = new File("C:\\Users\\lucas\\Barcodes\\" + produto.produtoID + ".png");
                file.createNewFile();

                //grava o conte[udoi do codigo de barras
                try (OutputStream outputStream = new FileOutputStream(file)) {
                    BarcodeImageHandler.writePNG(bc, outputStream);
                }
            } else {
                //exibe mensagem de erro se o fornecedor não existir
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERRO");
                alert.setContentText("Você não pode adicionar um item\nde um fornecedor inexistente!");
                alert.showAndWait();
            }
        }
    }

    //método para voltar para a tela anterior
    public void voltar() throws IOException {
        HelloApplication.setRoot("main-view");
    }

    //método para editar produto

    public static boolean disableQtd;
    @FXML
    public void editar() throws IOException, SQLException {
        disableQtd = true;
        produto = null; //define variável global 'produto' como nula para prevenção de bugs
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem(); //define uma váriavel Produto para inserir o item selecionado
        produto = produtoSelecionado; //coloca essa variável dentro da váriável global produto
        HelloApplication.showModal("produto-modal-view"); //abre o modal de edição de produto

        //edita o produto na tabela e no BD se o fornecedor descrito existir no BD

        ProdutoDAO produtoDAOcheck = new ProdutoDAO();
        if (produtoDAOcheck.checkFornecedor(produto.fornecedorID)) {
            produtoSelecionado.fornecedorID = produto.fornecedorID;
            produtoSelecionado.nomeProduto = produto.nomeProduto;
            produtoSelecionado.marca = produto.marca;
            produtoSelecionado.descricao = produto.descricao;
            produtoSelecionado.preco = produto.preco;
            produtoSelecionado.quantidade = produto.quantidade;
            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.update(produto);
            tabelaProdutos.refresh();
        } else {
            //exibe mensagem de erro se o fornecedor não existir
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERRO");
            alert.setContentText("Você não pode adicionar um item\nde um fornecedor inexistente!");
            alert.showAndWait();
        }
    }

    //método que desabilita botões excluir e editar enquanto nenhum produto da tabela estiver selecionado
    @FXML
    public void habilitarBotoes() {
        BooleanBinding algoSelecionado = tabelaProdutos.getSelectionModel().selectedItemProperty().isNull();
        excuir.disableProperty().bind(algoSelecionado);
        editar.disableProperty().bind(algoSelecionado);
        entraSaiButtom.disableProperty().bind(algoSelecionado);
    }

    //método que exclui o item selecionado da tabela e BD
    @FXML
    public void excluir() throws SQLException {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem(); //define uma váriavel Produto para inserir o item selecionado
        //gera uma confirmação de exclusão
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText(produtoSelecionado.nomeProduto);
        alert.setContentText("Deseja excluir esse produto?");
        Optional<ButtonType> result = alert.showAndWait();
        //se o usuário clicar em ok, remove o produto selecionado da tabela e BD
        if (result.get() == ButtonType.OK) {
            tabelaProdutos.getItems().remove(produtoSelecionado);
            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.delete(produtoSelecionado);
        }
    }
    @FXML
    public void entraSaiEstoque() throws SQLException, IOException {
        produto = null; //define variável global 'produto' como nula para prevenção de bugs
        produto = tabelaProdutos.getSelectionModel().getSelectedItem(); //coloca essa variável dentro da váriável global produto
        HelloApplication.showModal("quantidade-modal-view");

        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.update(produto);
        tabelaProdutos.refresh();
    }
    @FXML
    public void pesquisa() {

    }
}
