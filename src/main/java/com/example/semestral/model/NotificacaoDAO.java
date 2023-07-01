package com.example.semestral.model;

import com.example.semestral.ConnectionSingleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NotificacaoDAO {
    public List<Notificacao> getAll() throws SQLException {

        String sql = "select * from notificacao"; //código sql
        //conecta ao BD
        try(Statement statement = ConnectionSingleton.getConnection().createStatement()) {

            try (ResultSet rs = statement.executeQuery(sql)) {
                List<Notificacao> notificacoes = new ArrayList<>();
                //enquanto o cursor do 'rs.next' conseguir se mover para frente ele executa esse laço 'while' e vai adicionando todos os produtos do BD na tabela
                while (rs.next()) {
                    Notificacao notificacao = new Notificacao();
                    notificacao.notificacaoID = rs.getInt(1);
                    notificacao.produtoID = rs.getInt(2);
                    notificacao.notificaTexto = rs.getString(3);
                    notificacoes.add(notificacao);
                }
                return notificacoes;
            }
        }
    }
    public void insert(Notificacao novaNotificacao) throws SQLException {
        String sql = "insert into notificacao (notificacaoID, produtoID, notificaTexto) values (?, ?, ?);"; //código sql
        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //define os dados inseridos em cada campo da tabela fornecedor no BD
            preparedStatement.setInt(1, novaNotificacao.notificacaoID);
            preparedStatement.setInt(2, novaNotificacao.produtoID);
            preparedStatement.setString(3, novaNotificacao.notificaTexto);

            preparedStatement.execute(); //executa o código sql

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                novaNotificacao.notificacaoID = resultSet.getInt(1);
            }
        }
    }
    public void delete(Notificacao notificacaoSelecionada) throws SQLException {

        String sql = "delete from notificacao where notificacaoID = ?"; //código sql

        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql)) {

            preparedStatement.setInt(1, notificacaoSelecionada.notificacaoID); //diz qual o código do produto para ser excluido
            preparedStatement.execute(); //executa o código sql
        }
    }
}
