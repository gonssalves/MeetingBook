package com.ifal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ifal.bo.abstracts.UsuarioBase;

// DAO
public class Usuario {
    private Connection conexao;

    public Usuario(Connection conexao) {
        this.conexao = conexao;
    }

    public void adicionarUsuario(UsuarioBase sala) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
    
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, sala.getNome());
            statement.setString(2, sala.getEmail());
            statement.setString(3, sala.getSenha());
            statement.setString(4, sala.obterTipoUsuario()); // Método para obter o tipo da sala
    
            statement.executeUpdate();
        }
    }

    public void removerUsuario(int numero) throws SQLException {
        String sql = "DELETE FROM salas_de_reuniao WHERE numero = ?";
    
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, numero);
    
            statement.executeUpdate();
        }
    }
}
