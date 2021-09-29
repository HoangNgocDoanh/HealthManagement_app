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

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> adapter;
    Intent intent;

    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();
    Statement functionStatement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);

        searchView = findViewById(R.id.mySearchView);
        listView = findViewById(R.id.searchpatients);
        intent = getIntent();

        ArrayList<String> ListDoctor = new ArrayList<>();
        ArrayList<String> ListDoctorId = new ArrayList<>();

        ListDoctor = getDoctor();
        ListDoctorId = getDoctorId();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListDoctor);

        listView.setAdapter(adapter);

        final ArrayList<String> finalListDoctorId = ListDoctorId;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent DoctorInfo = new Intent(SearchActivity.this, MyDoctorInfoActivity.class);
                DoctorInfo.putExtra("ID_DOCTOR", finalListDoctorId.get(position));
                DoctorInfo.putExtra("ID_PATIENT_USER",intent.getStringExtra("ID_PATIENT_USER") != null ? intent.getStringExtra("ID_PATIENT_USER") : "");
                startActivity(DoctorInfo);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        MenuItem menuItem = menu.findItem(R.id.searchpatients);
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

    public ArrayList<String> getDoctor() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            functionStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = functionStatement.executeQuery("select * from res_doctor_GetAll() order by did desc");
            while (resultSet.next()) {
                arrayList.add(resultSet.getString("fullName") != null ? resultSet.getString("fullName") : "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<String> getDoctorId() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            functionStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = functionStatement.executeQuery("select * from res_doctor_GetAll() order by did desc");
            while (resultSet.next()) {
                arrayList.add(resultSet.getString("did") != null ? resultSet.getString("did") : "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
