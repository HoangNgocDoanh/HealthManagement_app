package com.cuoiky.smartdoctor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {

    Intent intent;
    Button btnPickDate,btnconfirm,btncancel;
    DatePickerDialog.OnDateSetListener dateSetListener;
    String date;

    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        intent = getIntent();

        btnPickDate = findViewById(R.id.pickDayButton);
        btnconfirm = findViewById(R.id.button2);
        btncancel = findViewById(R.id.button3);

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ScheduleActivity.this,
                        R.style.Theme_AppCompat_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + month + "/" + year;
                Toast.makeText(ScheduleActivity.this,"Bạn đã chọn ngày "+ dayOfMonth + "/" + month + "/" + year +"!",Toast.LENGTH_LONG).show();
            }
        };

    }

    public void confirm(View view) {
        String patient_id = intent.getStringExtra("ID_USER");
        try {
            CallableStatement statement = con.prepareCall("exec res_patient_addSchedule ?,?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,patient_id);
            statement.setString(2,date);
            if (!statement.execute()) {
                Toast.makeText(ScheduleActivity.this,"Bạn đã đặt lịch hẹn thành công!",Toast.LENGTH_LONG).show();
                finish();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancel(View view) {
        finish();
    }
}
