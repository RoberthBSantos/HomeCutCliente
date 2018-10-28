package com.example.roberthsantos.homecutcliente.Models;

public class Servico {
    public String getIdServico() {
        return idServico;
    }

    public String getNome() {
        return nome;
    }

    public Double getValor() {
        return valor;
    }

    private String idServico;
    private String nome;
    private Double valor;

    public Servico(String idServico, String nome, Double valor) {
        this.idServico = idServico;
        this.nome = nome;
        this.valor = valor;
    }

    public Servico(){

    }
}
