package com.example.semestral.model;

import com.example.semestral.ConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    //método para buscar itens no BD e colocar na tabela
    public List<Produto> getAll() throws SQLException {

        String sql = "select * from produtos"; //código sql
        //conecta ao BD
        try(Statement statement = ConnectionSingleton.getConnection().createStatement()) {

            try (ResultSet rs = statement.executeQuery(sql)) {
                List<Produto> produtos = new ArrayList<>();
                //enquanto o cursor do 'rs.next' conseguir se mover para frente ele executa esse laço 'while' e vai adicionando todos os produtos do BD na tabela
                while (rs.next()) {
                    Produto produto = new Produto();
                    produto.produtoID = rs.getInt(1);
                    produto.fornecedorID = rs.getInt(2);
                    produto.nomeProduto = rs.getString(3);
                    produto.marca = rs.getString(4);
                    produto.descricao = rs.getString(5);
                    produto.quantidade = rs.getInt(6);
                    produto.unidadeMedida = rs.getString(7);
                    produto.preco = rs.getDouble(8);
                    produto.quantidadeMinima = rs.getInt(9);
                    produtos.add(produto);
                }
                return produtos;
            }
        }
    }

    //método para inserir dados no BD
    public void insert(Produto novoProduto) throws SQLException {
        String sql = "insert into produtos (produtoID, fornecedorID, nomeproduto, marca, descricao, quantidade, unidademedida, preco, quantidademinima) values (?, ?, ?, ?, ?, ?, ?, ?, ?);"; //código sql
        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //define os dados inseridos em cada campo da tabela fornecedor no BD
            preparedStatement.setInt(1, novoProduto.produtoID);
            preparedStatement.setInt(2, novoProduto.fornecedorID);
            preparedStatement.setString(3, novoProduto.nomeProduto);
            preparedStatement.setString(4,novoProduto.marca);
            preparedStatement.setString(5,novoProduto.descricao);
            preparedStatement.setInt(6,novoProduto.quantidade);
            preparedStatement.setString(7, novoProduto.unidadeMedida);
            preparedStatement.setDouble(8, novoProduto.preco);
            preparedStatement.setInt(9, novoProduto.quantidadeMinima);
            preparedStatement.execute(); //executa o código sql

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                novoProduto.produtoID = resultSet.getInt(1);
            }
        }
    }

    //deleta um produto do BD
    public void delete(Produto produtoSelecionado) throws SQLException {

        String sql = "delete from produtos where produtoID = ?"; //código sql

        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql)) {

            preparedStatement.setInt(1, produtoSelecionado.produtoID); //diz qual o código do produto para ser excluido
            preparedStatement.execute(); //executa o código sql
        }
    }

    //edita um produto do BD
    public void update(Produto produtoSelecionado) throws SQLException {

        String sql = "update produtos set produtoID = ?, fornecedorID = ?, nomeproduto = ?, marca = ?, descricao = ?, quantidade = ?, unidademedida = ?, preco = ?, quantidademinima = ? where produtoID = ?"; //código sql

        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql)) {

            //define os dados inseridos em cada campo da tabela fornecedor no BD
            preparedStatement.setInt(1, produtoSelecionado.produtoID);
            preparedStatement.setInt(2, produtoSelecionado.fornecedorID);
            preparedStatement.setString(3, produtoSelecionado.nomeProduto);
            preparedStatement.setString(4,produtoSelecionado.marca);
            preparedStatement.setString(5,produtoSelecionado.descricao);
            preparedStatement.setInt(6,produtoSelecionado.quantidade);
            preparedStatement.setString(7, produtoSelecionado.unidadeMedida);
            preparedStatement.setDouble(8, produtoSelecionado.preco);
            preparedStatement.setInt(9, produtoSelecionado.quantidadeMinima);
            preparedStatement.setInt(10, produtoSelecionado.produtoID);
            preparedStatement.execute(); //executa o código sql

        }
    }

    //verifica a existência do fornecedor
    public boolean checkFornecedor(int fornecedorID) throws SQLException {

        String sql = "select count(*) from fornecedor where fornecedorID = ?"; //código sql

        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql)) {

            //realiza a contagem de fornecedores com o código fornecido
            preparedStatement.setInt(1, fornecedorID);
            ResultSet resultado = preparedStatement.executeQuery();
            resultado.next();
            int quantidadeFornecedor = resultado.getInt(1);

            return quantidadeFornecedor > 0; //retorna se existe mais de 0 fornecedores com esse código fornecido

        }
    }
}
