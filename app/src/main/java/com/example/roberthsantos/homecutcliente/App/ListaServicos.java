package com.example.roberthsantos.homecutcliente.App;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.roberthsantos.homecutcliente.Adapters.ListaServicosAdapter;
import com.example.roberthsantos.homecutcliente.Models.Servico;
import com.example.roberthsantos.homecutcliente.Models.ServicosSelecionados;
import com.example.roberthsantos.homecutcliente.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaServicos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView rvListaServicos;
    DatabaseReference databaseServicos;
    List<Servico> servicos;
    List<Servico> selecionados = new ArrayList<>();
    ListaServicosAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_lista_servicos);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//
//        setupDrawer(toolbar);

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

    private void setupDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void criarServico(View view) {

//        ServicosSelecionados.deleteAll(ServicosSelecionados.class);
//
//        ServicosSelecionados escolhidos = new ServicosSelecionados();
//
//        escolhidos.setServicos(adapter.servicosChecados);
//        escolhidos.save();


        adapter = new ListaServicosAdapter(this,servicos);
        selecionados = adapter.servicosChecados;
        //Toast.makeText(this, "" + adapter.servicosChecados.size(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,AgendamentoActivity.class);

        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.menu_principal){
            Intent intent = new Intent(this,MeusAgendamentosActivity.class);
            startActivity(intent);

        }
        if(item.getItemId() == R.id.logout){
            logout();

        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("AUTH", "USUARIO DESCONECTADO");
                        Toast.makeText(ListaServicos.this, "Desconectado.", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                });
    }
}
