package com.ifal.bo.abstracts;

public abstract class SalaDeReuniaoBase {
    private int numero;
    private int capacidade;
    private double precoHora;

    public SalaDeReuniaoBase(int numero, int capacidade, double precoHora) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.precoHora = precoHora;
    }

    // Getters
    public int getNumero(){
        return this.numero;
    }

    public int getCapacidade(){
        return this.capacidade;
    }

    public double getPrecoHora(){
        return this.precoHora;
    }

    // Setters
     public int setNumero(int numero){
        return this.numero = numero;
    }

    public int setCapacidade(int capacidade){
        return this.capacidade = capacidade;
    }

    public double setprecoHora(double precoHora){
        return this.precoHora = precoHora;
    }

    // Métodos que devem ser implementados pela subclasse
    public abstract double calcularPrecoReserva(int horas);

    public abstract String obterTipoSala();

    public abstract boolean temVideoConferencia();

    public abstract boolean possuiComputador();
}
