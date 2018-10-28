package com.example.roberthsantos.homecutcliente.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roberthsantos.homecutcliente.R;
import com.example.roberthsantos.homecutcliente.Models.Servico;

import java.util.List;

public class ListaServicosAdapter extends RecyclerView.Adapter<ListaServicosAdapter.ViewHolder> {
    @NonNull
    private final Context context;
    private final List<Servico> servicos;

    public ListaServicosAdapter(@NonNull Context context, List<Servico> servicos) {
        this.context = context;
        this.servicos = servicos;
    }

    @Override
    public ListaServicosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_servicos,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaServicosAdapter.ViewHolder viewHolder, int i) {

        final Servico servico = this.servicos.get(i);
        viewHolder.tvNomeServico.setText(servico.getNome());
        viewHolder.tvValorServico.setText("R$" + servico.getValor().toString());
    }

    @Override
    public int getItemCount() {
        return servicos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView tvNomeServico;
        protected TextView tvValorServico;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeServico = (TextView) itemView.findViewById(R.id.tv_servico_nome);
            tvValorServico = (TextView) itemView.findViewById(R.id.tv_servico_valor);
        }
    }
}
