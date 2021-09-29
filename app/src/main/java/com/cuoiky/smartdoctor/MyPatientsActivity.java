package com.cuoiky.smartdoctor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MyPatientsActivity extends AppCompatActivity {
    Intent oldIntent;
    SharedPreferences sharedPreferences;
    ListView listView;
    ArrayAdapter<String> adapter;
    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();
    Statement functionStatement;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients);
        oldIntent = getIntent();
        listView = findViewById(R.id.myPatients);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        ArrayList<String> ListPatient = new ArrayList<>();
        ArrayList<String> ListPatientId;
        ListPatient = getPatients();
        ListPatientId = getPatientId();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListPatient);
        listView.setAdapter(adapter);
        final ArrayList<String> finalListPatientsId = ListPatientId;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent PatientsInfo = new Intent(MyPatientsActivity.this, DisplayPatientInfo.class);
                PatientsInfo.putExtra("ID_PATIENTS", finalListPatientsId.get(position));
                PatientsInfo.putExtra("ID_DOCTOR_USER",oldIntent.getStringExtra("ID_DOCTOR_USER"));
                startActivity(PatientsInfo);
            }
        });
    }

    /// Get Patients
    public ArrayList<String> getPatients() {
        String Doctor_id = "";
        if (oldIntent != null && oldIntent.getStringExtra("ID_USER") != null) {
            Doctor_id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Doctor_id = sharedPreferences.getString("ID_USER","");
        }
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            functionStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = functionStatement.executeQuery("select * from res_doctor_res_patients_GetById("+Doctor_id+")");
            while (resultSet.next()) {
                arrayList.add(resultSet.getString("fullName") != null ? resultSet.getString("fullName") : "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public ArrayList<String> getPatientId() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            functionStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = functionStatement.executeQuery("select * from res_doctor_res_patients_GetAll()");
            while (resultSet.next()) {
                arrayList.add(resultSet.getString("pid") != null ? resultSet.getString("pid") : "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
