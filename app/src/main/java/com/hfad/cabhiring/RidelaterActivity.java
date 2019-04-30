package com.hfad.cabhiring;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class RidelaterActivity extends AppCompatActivity {

    EditText tp;
    EditText dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        dp = findViewById(R.id.dp);
        dp.setText(new StringBuilder().append(day).append("-")
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(year).append(" "));



        DatePicker datePick = findViewById(R.id.datePick);
//        datePick.setOnDateChangedListener(this);


        tp = findViewById(R.id.tp);
        tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog tpDialog = new TimePickerDialog(RidelaterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hr, int min) {
                        tp.setText(hr+":"+min);
                    }
                },currentHour,currentMinute,false);

                tpDialog.show();
            }
        });
    }

//    @Override
//    public void onDateChanged(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
//
//        // set selected date into textview
//        dp = findViewById(R.id.dp);
//        dp.setText(new StringBuilder().append(selectedDay).append("-").append(selectedMonth + 1)
//                .append("-").append(selectedYear)
//                .append(" "));
//
//
//    }

}