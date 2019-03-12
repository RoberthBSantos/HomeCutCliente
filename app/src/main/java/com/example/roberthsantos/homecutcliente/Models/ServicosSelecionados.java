package com.example.roberthsantos.homecutcliente.Models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class ServicosSelecionados extends SugarRecord {
    Servico servicos;

    public Servico getServicos() {
        return servicos;
    }

    public void setServicos(Servico servicos) {
        this.servicos = servicos;
    }

    public ServicosSelecionados() {
    }
}
