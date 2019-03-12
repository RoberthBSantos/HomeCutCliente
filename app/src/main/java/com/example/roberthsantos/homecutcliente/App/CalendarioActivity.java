package com.example.roberthsantos.homecutcliente.App;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.roberthsantos.homecutcliente.R;

import java.util.Date;

public class CalendarioActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView minhaData;
    long data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        minhaData = (TextView) findViewById(R.id.minhaData);


//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@androidx.annotation.NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                String date =    dayOfMonth + "/" + (month + 1) +"/" + year;
//                minhaData.setText(date);
//                data = calendarView.getDate();
//            }
//        });
    }

    public void selecionarData(View view) {
        Intent intent = new Intent(this,AgendamentoActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);

    }
}
