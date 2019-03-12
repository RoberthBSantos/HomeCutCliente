package com.example.roberthsantos.homecutcliente.App;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roberthsantos.homecutcliente.Adapters.ConfiguracaoFirebase;
import com.example.roberthsantos.homecutcliente.Models.Agendamento;
import com.example.roberthsantos.homecutcliente.Models.Endereco;
import com.example.roberthsantos.homecutcliente.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocalizacaoActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private EditText meuLocal;
    List<Agendamento> agendamentos = Agendamento.listAll(Agendamento.class);

    DatabaseReference databaseAgendamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);
        databaseAgendamento = FirebaseDatabase.getInstance().getReference("agendamentos");

       inicializarComponentes();

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        recuperarLocalizacaoUsuario();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Localização", "onLocationChanged: " + location.toString());

                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                mMap.clear();
                LatLng localUsuario = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(localUsuario).title("Meu local"));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localUsuario, 20));


                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    //List<Address> listaEndereco = geocoder.getFromLocation(latitude,longitude,1);
                    String stringEndereco = "R. Álvaro Mendes, 1399-1341 - Centro (Sul)";
                    List<Address> listaEndereco = geocoder.getFromLocationName(stringEndereco, 1);
                    if (listaEndereco != null && listaEndereco.size() > 0) {
                        Address endereco = listaEndereco.get(0);
                        Log.d("local", "onLocationChanged: " + endereco.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


    }

    private void recuperarLocalizacaoUsuario() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng meuLocal = new LatLng(latitude, longitude);

                mMap.clear();
                mMap.addMarker(
                        new MarkerOptions()
                                .position(meuLocal)
                                .title("Meu Local")
                );
                mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(meuLocal, 17)
                );
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000,
                    10,
                    locationListener
            );
        }

    }

    private void inicializarComponentes(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Local de Atendimento");
        setSupportActionBar(toolbar);


        meuLocal = findViewById(R.id.editLocal);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void salvarLocal(View view) {

        String enderecoUsuario = meuLocal.getText().toString();

        if(enderecoUsuario.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Confime o seu endereço!")
                    .setMessage("Localização atual.")
                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //SANVAR NO BANCO DE DADOS.
//                                String id = databaseAgendamento.push().getKey();
//                                Agendamento agendamento = new Agendamento(id,agendamentos.get(0).getUser(),agendamentos.get(0).getDate(),agendamentos.get(0).getHorario(),endereco.getLatitude(),endereco.getLongitude());
//                                salvarAgendamento(agendamento);
                            Toast.makeText(LocalizacaoActivity.this, "Agendamento concluído!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LocalizacaoActivity.this,ListaServicos.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        if( !enderecoUsuario.equals("") || enderecoUsuario != null){

            Address addressUsuario = recuperarEndereco(enderecoUsuario);
            if(addressUsuario != null){

                Endereco endereco = new Endereco();
                endereco.setCidade(addressUsuario.getSubAdminArea());
                endereco.setCep(addressUsuario.getPostalCode());
                endereco.setBairro(addressUsuario.getSubLocality());
                endereco.setRua(addressUsuario.getThoroughfare());
                endereco.setNumero(addressUsuario.getFeatureName());
                endereco.setLatitude(String.valueOf(addressUsuario.getLatitude()));
                endereco.setLongitude(String.valueOf(addressUsuario.getLongitude()));

                StringBuilder mensagem = new StringBuilder();

                mensagem.append("Cidade: " + endereco.getCidade());
                mensagem.append("\nBairro: " + endereco.getBairro());
                mensagem.append("\nRua: " + endereco.getRua());
                mensagem.append("\nNumero: " + endereco.getNumero());
                mensagem.append("\nCep: " + endereco.getCep());

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Confime o seu endereço!")
                        .setMessage(mensagem)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //SANVAR NO BANCO DE DADOS.
//                                String id = databaseAgendamento.push().getKey();
//                                Agendamento agendamento = new Agendamento(id,agendamentos.get(0).getUser(),agendamentos.get(0).getDate(),agendamentos.get(0).getHorario(),endereco.getLatitude(),endereco.getLongitude());
//                                salvarAgendamento(agendamento);
                                Toast.makeText(LocalizacaoActivity.this, "Agendamento concluído!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LocalizacaoActivity.this,ListaServicos.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }else {
            Intent intent = new Intent(LocalizacaoActivity.this,ListaServicos.class);
            startActivity(intent);
            Toast.makeText(this, "Confirme sua Localização", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarAgendamento(Agendamento agendamento){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference agendamentos = firebaseRef.child("agendamentos");

        String idAgendamento = agendamentos.push().getKey();

        agendamento.setId(idAgendamento);

        agendamentos.child(String.valueOf(agendamento.getId())
        ).setValue(this);



        Toast.makeText(this, "Agendado!", Toast.LENGTH_SHORT).show();
        finish();
    }


    private Address recuperarEndereco(String endereco){

        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
        try {
            List<Address> listaEnderecos = geocoder.getFromLocationName(endereco,1);

            if(listaEnderecos != null && listaEnderecos.size() > 0){

                Address address = listaEnderecos.get(0);

                return address;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
