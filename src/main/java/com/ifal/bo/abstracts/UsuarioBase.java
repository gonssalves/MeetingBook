package com.ifal.bo.abstracts;

public abstract class UsuarioBase {
    private String nome;
    private String email;
    private String senha;

    public UsuarioBase(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters
    public String getNome(){
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    // Setters
    public String setNome(String nome) {
        return this.nome = nome;
    }

    public String setEmail(String email) {
        return this.email = email;
    }

    public String setSenha(String senha) {
        return this.senha = senha;
    }

    // Métodos que devem ser implementados pela subclasse
    public abstract String obterTipoUsuario();
}
