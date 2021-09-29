package com.cuoiky.smartdoctor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorMenuActivity extends AppCompatActivity {
    Intent oldIntent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferencesEditor;
    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        oldIntent= getIntent();
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
    }
    public void profileInfo(View view) {
        String Id = "";
        if (oldIntent != null) {
            Id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Id = sharedPreferences.getString("ID_USER","");
        }
        Intent profile = new Intent(this, EditProfileDoctorActivity.class);
        profile.putExtra("ID_USER",Id);
        startActivity(profile);
    }
    public void myAppointments(View view){
        String Id = "";
        if (oldIntent != null && oldIntent.getStringExtra("ID_USER") != null) {
            Id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Id = sharedPreferences.getString("ID_USER","");
        }
        Intent myAppointment = new Intent(this, AppointmentsActivity.class);
        myAppointment.putExtra("ID_DOCTOR_USER",Id);
        startActivity(myAppointment);
    }
    public void  myPatients(View view){
        String Id = "";
        if (oldIntent != null && oldIntent.getStringExtra("ID_USER") != null) {
            Id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Id = sharedPreferences.getString("ID_USER","");
        }
        Intent mypatients = new Intent(this, MyPatientsActivity.class);
        mypatients.putExtra("ID_DOCTOR_USER",Id);
        startActivity(mypatients);
    }
    public void  searchPatients(View view){
        String Id = "";
        if (oldIntent != null && oldIntent.getStringExtra("ID_USER") != null) {
            Id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Id = sharedPreferences.getString("ID_USER","");
        }
        Intent search = new Intent(this, SearchPatientsActivity.class);
        search.putExtra("ID_DOCTOR_USER",Id);
        startActivity(search);
    }
    public void  logOut(View view){
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putBoolean("patient",false);
        preferencesEditor.putBoolean("doctor",false);
        preferencesEditor.putString("ID_USER","");
        preferencesEditor.commit();
        Intent logOut = new Intent(this, Login.class);
        startActivity(logOut);
    }
}
