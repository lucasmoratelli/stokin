package com.example.semestral.model;

import com.example.semestral.ConnectionSingleton;

import java.sql.*;

public class UsuarioDAO {
    public boolean existe(Usuario usuario) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement("select count(*) from usuario where usuario = ? AND senha = ?")) {
            preparedStatement.setString(1, usuario.usuario);
            preparedStatement.setString(2, usuario.senha);
            ResultSet resultado = preparedStatement.executeQuery();
            resultado.next();
            int quantidadeUsuarios = resultado.getInt(1);
            return quantidadeUsuarios > 0;
        }
    }

    public int acessLevel(Usuario usuario) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement("select nivelacesso from usuario where usuario = ? AND senha = ?")) {
            preparedStatement.setString(1, usuario.usuario);
            preparedStatement.setString(2, usuario.senha);
            ResultSet resultado = preparedStatement.executeQuery();
            resultado.next();
            return resultado.getInt(1);
        }
    }
}
