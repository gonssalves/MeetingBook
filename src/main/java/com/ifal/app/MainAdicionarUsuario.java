package com.ifal.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ifal.bo.concretes.Gerente;
import com.ifal.dao.UsuarioDAO;

public class MainAdicionarUsuario {
    public static void main(String[] args) {
    Connection conexao = null;
    try {
        conexao = DriverManager.getConnection("jdbc:sqlite:meetingbook.db");
        
        Gerente gerente1 = new Gerente("Hans Landa", "hans_gerente@gmail.com", "1234abc");
        
        UsuarioDAO conexaoUsuario = new UsuarioDAO(conexao);
        
        conexaoUsuario.adicionarUsuario(gerente1);
        
    } catch(SQLException e) {
        System.err.println(e.getMessage());
    }

    finally {
        try {
        if(conexao != null){
        conexao.close();
        }
        } catch(SQLException e) {
        // Falhou também para fechar o arquivo
        System.err.println(e.getMessage());
        }
    }

    }
}
