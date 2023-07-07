package com.example.semestral.model;

import com.example.semestral.ConnectionSingleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {
    //método para buscar fornecedores no BD e colocar na tabela
    public List<Fornecedor> getAll() throws SQLException {

        String sql = "select * from fornecedor"; //código sql
        //conecta ao BD
        try(Statement statement = ConnectionSingleton.getConnection().createStatement()) {

            try (ResultSet rs = statement.executeQuery(sql)) {
                List<Fornecedor> fornecedores = new ArrayList<>();
                //enquanto o cursor do 'rs.next' conseguir se mover para frente ele executa esse laço 'while' e vai adicionando todos os produtos do BD na tabela
                while (rs.next()) {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.fornecedorID = rs.getInt(1);
                    fornecedor.nomeFornecedor = rs.getString(2);
                    fornecedor.CNPJ = rs.getString(3);
                    fornecedor.telefone = rs.getString(4);
                    fornecedor.site = rs.getString(5);
                    fornecedor.pais = rs.getString(6);
                    fornecedores.add(fornecedor);
                }
                return fornecedores;
            }
        }
    }
    public void insert(Fornecedor novoFornecedor) throws SQLException {
        String sql = "insert into fornecedor (fornecedorID, nomeFornecedor, CNPJ, telefone, site, pais) values (?, ?, ?, ?, ?, ?);"; //código sql
        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //define os dados inseridos em cada campo da tabela produtos no BD
            preparedStatement.setInt(1, novoFornecedor.fornecedorID);
            preparedStatement.setString(2, novoFornecedor.nomeFornecedor);
            preparedStatement.setString(3, novoFornecedor.CNPJ);
            preparedStatement.setString(4,novoFornecedor.telefone);
            preparedStatement.setString(5,novoFornecedor.site);
            preparedStatement.setString(6,novoFornecedor.pais);
            preparedStatement.execute(); //executa o código sql

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                novoFornecedor.fornecedorID = resultSet.getInt(1);
            }
        }
    }
    public void delete(Fornecedor fornecedorSelecionado) throws SQLException {

        String sql = "delete from fornecedor where fornecedorID = ?"; //código sql

        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql)) {

            preparedStatement.setInt(1, fornecedorSelecionado.fornecedorID); //diz qual o código do produto para ser excluido
            preparedStatement.execute(); //executa o código sql
        }
    }
    public void update(Fornecedor fornecedorSelecionado) throws SQLException {

        String sql = "update fornecedor set fornecedorID = ?, nomeFornecedor = ?, CNPJ = ?, telefone = ?, site = ?, pais = ? where fornecedorID = ?"; //código sql

        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql)) {

            //define os dados inseridos em cada campo da tabela fornecedor no BD
            preparedStatement.setInt(1, fornecedorSelecionado.fornecedorID);
            preparedStatement.setString(2, fornecedorSelecionado.nomeFornecedor);
            preparedStatement.setString(3, fornecedorSelecionado.CNPJ);
            preparedStatement.setString(4,fornecedorSelecionado.telefone);
            preparedStatement.setString(5,fornecedorSelecionado.site);
            preparedStatement.setString(6,fornecedorSelecionado.pais);
            preparedStatement.execute(); //executa o código sql
        }
    }
}
