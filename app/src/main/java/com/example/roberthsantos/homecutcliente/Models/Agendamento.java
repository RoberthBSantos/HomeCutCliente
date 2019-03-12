package com.example.roberthsantos.homecutcliente.Models;

import com.google.firebase.auth.FirebaseUser;
import com.orm.SugarRecord;

public class Agendamento extends SugarRecord {
    private String idAgendamento;
    private FirebaseUser user;
    private String date;
    private String horario;
    private String latitude;
    private String longitude;

    public Agendamento(String idAgendamento, FirebaseUser user, String date, String horario, String latitude, String longitude) {
        this.idAgendamento = idAgendamento;
        this.user = user;
        this.date = date;
        this.horario = horario;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Agendamento() {
    }


    public String getIdAgendamento() {
        return idAgendamento;
    }

    public void setId(String id) {
        this.idAgendamento = idAgendamento;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
