package com.cuoiky.smartdoctor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;

public class AppointmentsActivity extends AppCompatActivity {
    Intent oldIntent;
    SharedPreferences sharedPreferences;
    ListView listView;
    ArrayAdapter<String> adapter;
    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();
    CallableStatement statement;
    Statement functionStatement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        oldIntent = getIntent();

        listView = findViewById(R.id.appointmentlist);

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        ArrayList<String> ListAppointments;

        ListAppointments = getAppointments();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListAppointments);
        listView.setAdapter(adapter);
    }
    public ArrayList<String> getAppointments() {
        String Doctor_id = "";
        if (oldIntent != null && oldIntent.getStringExtra("ID_USER") != null) {
            Doctor_id = oldIntent.getStringExtra("ID_USER");
        }
        else {
            Doctor_id = sharedPreferences.getString("ID_USER","");
        }
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String Apm;
            functionStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = functionStatement.executeQuery("select * from res_doctor_res_Appointment("+Doctor_id+")");
            if (!resultSet.next() ) {
                Toast.makeText(this,"No Data For Appointment !",Toast.LENGTH_LONG).show();
            } else {
                do {
                    Apm = resultSet.getString("DOH") + " - " + resultSet.getString("fullname");
                    arrayList.add(Apm);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
