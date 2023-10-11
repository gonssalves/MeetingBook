package com.ifal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ifal.bo.abstracts.UsuarioBase;

// DAO
public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void adicionarUsuario(UsuarioBase usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha, funcao_id) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.setInt(4, usuario.obterIdFuncao()); // Método para obter o id da função do usuário
    
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
