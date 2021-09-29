package com.cuoiky.smartdoctor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    Intent oldIntent;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferencesEditor;

    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oldIntent = getIntent();
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
    }

    public void Appointment(View view) {
        Intent appointment = new Intent(this, Appointments2Activity.class);
        String Id = "";
        if (oldIntent != null && oldIntent.getStringExtra("ID_USER") != null) {
            Id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Id = sharedPreferences.getString("ID_USER","");
        }
        appointment.putExtra("ID_USER",Id);
        startActivity(appointment);
    }

    public void profileInfo(View view) {
        String Id = "";
        if (oldIntent != null) {
            Id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Id = sharedPreferences.getString("ID_USER","");
        }
        Intent profile = new Intent(this, EditProfileActivity.class);
        profile.putExtra("ID_USER",Id);
        startActivity(profile);
    }

    public void myDoctors(View view) {
        Intent mydoctorInfo = new Intent(this, MyDoctorInfoActivity.class);
        String Id = "";
        if (oldIntent != null && oldIntent.getStringExtra("ID_USER") != null) {
            Id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Id = sharedPreferences.getString("ID_USER","");
        }
        mydoctorInfo.putExtra("ID_PATIENT_USER",Id);
        startActivity(mydoctorInfo);
    }

    public void logOut(View view) {
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putBoolean("patient",false);
        preferencesEditor.putBoolean("doctor",false);
        preferencesEditor.putString("ID_USER","");
        preferencesEditor.commit();
        Intent logOut = new Intent(this, Login.class);
        startActivity(logOut);
    }

    public void searchDoctor(View view) {
        Intent search = new Intent(this, SearchActivity.class);
        String Id = "";
        if (oldIntent != null && oldIntent.getStringExtra("ID_USER") != null) {
            Id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Id = sharedPreferences.getString("ID_USER","");
        }
        search.putExtra("ID_PATIENT_USER",Id);
        startActivity(search);
    }

    public void MedicalFolder(View view) {
        Intent medicalfolder = new Intent(this, MedicalFolderActivity.class);
        String Id = "";
        if (oldIntent != null && oldIntent.getStringExtra("ID_USER") != null) {
            Id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Id = sharedPreferences.getString("ID_USER","");
        }
        medicalfolder.putExtra("ID_USER",Id);
        startActivity(medicalfolder);
    }
}