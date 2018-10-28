package com.example.roberthsantos.homecutcliente.Models;

import com.firebase.ui.auth.data.model.User;

import java.util.Date;
import java.util.List;

public class Agendamento {
    private String id;
    private List<Servico> servicos;
    private User user;
    private Date date;
    private String horario;


    public Agendamento(String id, List<Servico> servicos, User user, Date date, String horario) {
        this.id = id;
        this.servicos = servicos;
        this.user = user;
        this.date = date;
        this.horario = horario;
    }

    public String getId() {
        return id;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public String getHorario() {
        return horario;
    }
}
