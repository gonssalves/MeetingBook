package com.ifal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ifal.bo.abstracts.SalaDeReuniaoBase;;

// DAO
public class SalaDeReuniaoDAO {
    private Connection conexao;

    public SalaDeReuniaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void adicionarSala(SalaDeReuniaoBase sala) throws SQLException {
        String sql = "INSERT INTO salas_de_reuniao (nome, capacidade, preco_hora, tipo) VALUES (?, ?, ?, ?)";
    
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, sala.getNumero());
            statement.setInt(2, sala.getCapacidade());
            statement.setDouble(3, sala.getPrecoHora());
            statement.setString(4, sala.obterTipoSala()); // Método para obter o tipo da sala
    
            statement.executeUpdate();
        }
    }

    public void removerSala(int numero) throws SQLException {
        String sql = "DELETE FROM salas_de_reuniao WHERE numero = ?";
    
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, numero);
    
            statement.executeUpdate();
        }
    }
}