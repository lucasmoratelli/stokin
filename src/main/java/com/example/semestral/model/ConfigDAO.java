package com.example.semestral.model;

import com.example.semestral.ConnectionSingleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConfigDAO {
    public void configBarcode(String parameter) throws SQLException {

        String sql = "update config set parameter = ? where configID = 1"; //código sql

        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql)) {

            //define os dados inseridos em cada campo da tabela fornecedor no BD
            preparedStatement.setString(1, parameter);

            preparedStatement.execute(); //executa o código sql

        }
    }
    public void configQRCode(String parameter) throws SQLException {
        String sql = "update config set parameter = ? where configID = 2"; //código sql

        //conecta ao BD
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql)) {

            //define os dados inseridos em cada campo da tabela fornecedor no BD
            preparedStatement.setString(1, parameter);

            preparedStatement.execute(); //executa o código sql

        }
    }
    public List<String> getConfigs() throws SQLException {

        String sql = "select * from config"; //código sql
        //conecta ao BD
        try(Statement statement = ConnectionSingleton.getConnection().createStatement()) {

            try (ResultSet rs = statement.executeQuery(sql)) {
                List<String> configs = new ArrayList<>();
                //enquanto o cursor do 'rs.next' conseguir se mover para frente ele executa esse laço 'while' e vai adicionando todos os produtos do BD na tabela
                while (rs.next()) {
                    String config;
                    config = rs.getString(3);

                    configs.add(config);
                }
                return configs;
            }
        }
    }
}
