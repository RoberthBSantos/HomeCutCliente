package com.example.roberthsantos.homecutcliente.App;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.roberthsantos.homecutcliente.Adapters.ListaServicosAdapter;
import com.example.roberthsantos.homecutcliente.Models.Servico;
import com.example.roberthsantos.homecutcliente.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaServicos extends AppCompatActivity {
    RecyclerView rvListaServicos;
    DatabaseReference databaseServicos;
    List<Servico> servicos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicos);
        servicos = new ArrayList<>();

        databaseServicos = FirebaseDatabase.getInstance().getReference("servicos");
        rvListaServicos = (RecyclerView) findViewById(R.id.rv_servicos);



        databaseServicos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                servicos.clear();

                for (DataSnapshot servicoSnapshot : dataSnapshot.getChildren()){
                    Servico servico = servicoSnapshot.getValue(Servico.class);

                    servicos.add(servico);
                }

                ListaServicosAdapter adapter = new ListaServicosAdapter(ListaServicos.this,servicos);
                rvListaServicos.setAdapter(adapter);
                rvListaServicos.setLayoutManager(new LinearLayoutManager(ListaServicos.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    public void criarServico(View view) {
        Intent intent = new Intent(this,AgendamentoActivity.class);
        startActivity(intent);
    }
}
