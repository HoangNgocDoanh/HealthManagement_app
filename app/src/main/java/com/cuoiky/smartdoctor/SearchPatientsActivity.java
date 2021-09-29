package com.cuoiky.smartdoctor;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchPatientsActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    Intent intent;
    ArrayAdapter<String> adapter;
    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();
    Statement functionStatement;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patients);


        searchView = findViewById(R.id.mySearchView2);
        listView = findViewById(R.id.searchpatients2);
        intent =getIntent();

        ArrayList<String> ListPatient = new ArrayList<>();
        ArrayList<String> ListPatientId = new ArrayList<>();

        ListPatient = getPatients();
        ListPatientId = getPatientId();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListPatient);
        listView.setAdapter(adapter);

        final ArrayList<String> finalListPatientsId = ListPatientId;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent PatientsInfo = new Intent(SearchPatientsActivity.this, DisplayPatientInfo.class);
                PatientsInfo.putExtra("ID_PATIENTS", finalListPatientsId.get(position));
                startActivity(PatientsInfo);
            }
        });
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        MenuItem menuItem = menu.findItem(R.id.searchpatients2);
        searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
/// Get Patients
    public ArrayList<String> getPatients() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            functionStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = functionStatement.executeQuery("select * from res_doctor_res_patients_GetAll()");
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
