package com.cuoiky.smartdoctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDoctorInfoActivity extends AppCompatActivity {

    Intent oldIntent;
    TextView fullName,DOB;
    Statement functionStatement;
    FloatingActionButton addbtn;
    Button schedule;

    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctors_info);

        fullName = findViewById(R.id.fullName_Info);
        DOB = findViewById(R.id.birthDate_Info);
        addbtn = findViewById(R.id.fab);
        schedule = findViewById(R.id.btnappointment);

        oldIntent = getIntent();
// vào từ mypatient
        if (oldIntent.getStringExtra("ID_DOCTOR") != null) {
            getDoctorforSearchActivity(oldIntent.getStringExtra("ID_DOCTOR"));
            addbtn.setVisibility(View.VISIBLE);
            schedule.setVisibility(View.GONE);
        }
        // vào từ search
        else {
            getMyDoctor();
            addbtn.setVisibility(View.GONE);
            schedule.setVisibility(View.VISIBLE);
        }
    }

    public void getMyDoctor() {
        String patient_id = oldIntent.getStringExtra("ID_PATIENT_USER");
        try {
             functionStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = functionStatement.executeQuery("select * from res_doctor_GetbyId("+ patient_id +")");
            if (resultSet.first()) {
                fullName.setText(resultSet.getString("fullName") != null ? resultSet.getString("fullName") : "");
                DOB.setText(resultSet.getString("DOB") != null ? resultSet.getString("DOB") : "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getDoctorforSearchActivity(String DoctorId) {
        try {
            CallableStatement statement = con.prepareCall("exec res_doctor_GetbyDoctorId ?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,DoctorId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.first()) {
                fullName.setText(resultSet.getString("fullName") != null ? resultSet.getString("fullName") : "");
                DOB.setText(resultSet.getString("DOB") != null ? resultSet.getString("DOB") : "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void phoneCall(View view) {

    }

    public void Gmail(View view) {

    }

    public void scheduleAppointment(View view) {
        String patient_id = oldIntent.getStringExtra("ID_PATIENT_USER");
        Intent schedule = new Intent(this, ScheduleActivity.class);
        schedule.putExtra("ID_USER",patient_id);
        startActivity(schedule);
    }

    public void addDoctor(View view) {
        try {
            CallableStatement statement = con.prepareCall("exec res_patient_AddDoctor ?,?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,oldIntent.getStringExtra("ID_PATIENT_USER"));
            statement.setString(2,oldIntent.getStringExtra("ID_DOCTOR"));
            statement.execute();
            if (!statement.execute()) {
                Toast.makeText(MyDoctorInfoActivity.this,"Your doctor now!",Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
