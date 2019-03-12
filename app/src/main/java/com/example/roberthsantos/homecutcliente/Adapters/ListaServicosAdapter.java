package com.example.roberthsantos.homecutcliente.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roberthsantos.homecutcliente.Models.ServicosSelecionados;
import com.example.roberthsantos.homecutcliente.R;
import com.example.roberthsantos.homecutcliente.Models.Servico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListaServicosAdapter extends RecyclerView.Adapter<ListaServicosAdapter.ViewHolder> {

    private final Context context;
    private final List<Servico> servicos;
    public List<Servico> servicosChecados = new ArrayList<>();
    public  List<Servico> teste;
    public ServicosSelecionados selecionados;

    public ListaServicosAdapter(@NonNull Context context, List<Servico> servicos) {
        this.context = context;
        this.servicos = servicos;
    }

    @NonNull
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
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox checkBox = (CheckBox) v;

                if(checkBox.isChecked()){
                    servicosChecados.add(servicos.get(pos));

                    ServicosSelecionados.deleteAll(ServicosSelecionados.class);

                    for(int i = 0; i < servicosChecados.size(); i++){

                        ServicosSelecionados escolhidos = new ServicosSelecionados();

                        escolhidos.setServicos(servicosChecados.get(i));


                        escolhidos.save();
                        Toast.makeText(context, "" + servicosChecados.get(i).getNome(), Toast.LENGTH_SHORT).show();
                    }


                }else if(!checkBox.isChecked()){
                    servicosChecados.remove(servicos.get(pos));
                    ServicosSelecionados.deleteAll(ServicosSelecionados.class);

                    for(int i = 0; i < servicosChecados.size(); i++){

                        ServicosSelecionados escolhidos = new ServicosSelecionados();

                        escolhidos.setServicos(servicosChecados.get(i));

                        escolhidos.save();
                        Toast.makeText(context, "" + servicosChecados.get(i).getNome(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return servicos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        protected TextView tvNomeServico;
        protected TextView tvValorServico;
        protected CheckBox checkBox;
        protected ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNomeServico = (TextView) itemView.findViewById(R.id.tv_servico_nome);
            tvValorServico = (TextView) itemView.findViewById(R.id.tv_servico_valor);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox_servico);

            checkBox.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener ic){

            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {

            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
    }
}
