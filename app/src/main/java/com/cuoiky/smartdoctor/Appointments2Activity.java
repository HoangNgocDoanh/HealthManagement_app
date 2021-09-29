package com.cuoiky.smartdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Appointments2Activity extends AppCompatActivity {

    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();
    CallableStatement statement;

    Intent intent;
    ListView listView;
    ArrayAdapter<String> adapter;
    String Id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_2);

        intent = getIntent();
        listView = findViewById(R.id.appointmentlist2);

        ArrayList<String> ListAppointments;

        ListAppointments = getAppointments();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListAppointments);

        listView.setAdapter(adapter);
    }

    public ArrayList<String> getAppointments() {
        if (intent != null && intent.getStringExtra("ID_USER") != null) {
            Id = intent.getStringExtra("ID_USER");
        }
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            String appointmentItem;
            statement = con.prepareCall("exec res_patient_GetAppointment ?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,Id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next() ) {
                appointmentItem = resultSet.getString("date") + " - " + resultSet.getString("name");
                arrayList.add(appointmentItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

}
