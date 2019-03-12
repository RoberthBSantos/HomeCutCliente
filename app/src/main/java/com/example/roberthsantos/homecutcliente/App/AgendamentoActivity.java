package com.example.roberthsantos.homecutcliente.App;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roberthsantos.homecutcliente.Models.Agendamento;
import com.example.roberthsantos.homecutcliente.Models.ServicosSelecionados;
import com.example.roberthsantos.homecutcliente.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AgendamentoActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    TextView tvData;
    TextView teste;
    Spinner horario;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);


        List<ServicosSelecionados> servicosSelecionados = ServicosSelecionados.listAll(ServicosSelecionados.class);
        //Toast.makeText(this, ""+ servicosSelecionados.get(0).getServicos(), Toast.LENGTH_LONG).show();


        tvData = (TextView) findViewById(R.id.tv_agendamento_data);
        teste = (TextView) findViewById(R.id.boraVer);
        horario = (Spinner) findViewById(R.id.agendamento_spinner);




        FirebaseAuth auth = FirebaseAuth.getInstance();
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .build(), RC_SIGN_IN);



        adapter = ArrayAdapter.createFromResource(this,R.array.horarios,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horario.setAdapter(adapter);
        horario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void logout(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("AUTH", "USUARIO DESCONECTADO");
                        finish();
                    }
                });
    }




    public void abrirDatePicker(View view) {
        final Calendar calendario = Calendar.getInstance();
        int mAno = calendario.get(Calendar.YEAR);
        int mMes = calendario.get(Calendar.MONTH);
        int mDia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =  new DatePickerDialog(this,(datePiker,year,month,day)->{
            calendario.set(year,month,day);
            calendario.set(Calendar.HOUR_OF_DAY,23);
            calendario.set(Calendar.MINUTE,59);
            teste.setText(new SimpleDateFormat("dd - MMMM - yyyy", Locale.getDefault())
                    .format(calendario.getTime()));
        },mAno,mMes,mDia);
        datePickerDialog.getDatePicker().setMinDate(calendario.getTimeInMillis());
        datePickerDialog.show();

    }

//    public void pegarData(View view) {
//        //Intent intent = new Intent(this,CalendarioActivity.class);
//        //startActivity(intent);
//
//        //tentando inflar um layout
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        final View viewDialog = getLayoutInflater().inflate(R.layout.activity_calendario,null);
//
//
//        builder.setView(viewDialog)
//                .setTitle("Selecionar Data")
//                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
//                        TextView minhaData = (TextView) findViewById(R.id.minhaData);
//
//                        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//                            @Override
//                            public void onSelectedDayChange(@androidx.annotation.NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                                String date =    dayOfMonth + "/" + (month + 1) +"/" + year;
//                                tvData.setText(date);
//                                //data = calendarView.getDate();
//                            }
//                        });
//                    }
//                })
//                .setNegativeButton("Cancelar",null)
//                .show();
//    }

    public void fazerAgendamento(View view) {
        if(teste.getText().toString() == "" ){
            Toast.makeText(this, "Selecione uma data!", Toast.LENGTH_SHORT).show();
        }else {
            Agendamento.deleteAll(Agendamento.class);
            Agendamento agendamento = new Agendamento();

            agendamento.setDate(tvData.getText().toString());
            agendamento.setHorario(horario.getSelectedItem().toString());
            agendamento.setUser(FirebaseAuth.getInstance().getCurrentUser());
            agendamento.save();
            //Toast.makeText(this, "Fazer metodo pra salvar no banco de dados", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LocalizacaoActivity.class);
            startActivity(intent);
        }
    }

}