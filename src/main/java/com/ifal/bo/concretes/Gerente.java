package com.ifal.bo.concretes;

import com.ifal.bo.abstracts.UsuarioBase;

public class Gerente extends UsuarioBase{
    public Gerente(String nome, String email, String senha){
        super(nome, email, senha);
    }   

    @Override
    public String obterTipoUsuario() {
        return "Gerente";
    }

    @Override
    public int obterIdFuncao() {
        return 1;
    }
}
