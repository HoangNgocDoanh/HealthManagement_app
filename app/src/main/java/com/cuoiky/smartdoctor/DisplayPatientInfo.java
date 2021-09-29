package com.cuoiky.smartdoctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DisplayPatientInfo extends AppCompatActivity {
    TextView fullName,DOB;
    Statement functionStatement;
    SharedPreferences sharedPreferences;
    FloatingActionButton addbtn;
    FloatingActionButton billbtn;
    Intent oldIntent;
    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients_info);

        oldIntent = getIntent();

        fullName = findViewById(R.id.fullName_Info_Patients);
        DOB = findViewById(R.id.birthDate_Info_Patients);
        addbtn = findViewById(R.id.fab);
        billbtn =findViewById(R.id.fab3);

        //vao tu my patient
        if (oldIntent.getStringExtra("ID_DOCTOR_USER") != null) {
            addbtn.setVisibility(View.VISIBLE);
            billbtn.setVisibility(View.VISIBLE);

        //vao tu search
        } else {
            addbtn.setVisibility(View.GONE);
            billbtn.setVisibility(View.GONE);
        }
        getpatientforSearchActivity(oldIntent.getStringExtra("ID_PATIENTS"));
    }


    public void getpatientforSearchActivity(String PatientId) {
        try {
            CallableStatement statement = con.prepareCall("exec res_Patients_GetbyPatientsId ?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,PatientId);
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

    public void addmedical(View view) {
        String Id = oldIntent.getStringExtra("ID_PATIENTS");
        Intent addmedical = new Intent(this, AddMedicalActivity.class);
        addmedical.putExtra("ID_PATIENTS",Id);
        startActivity(addmedical);

    }
    public void addbill (View view) {
        String p_id = oldIntent.getStringExtra("ID_PATIENTS");
        try {
            CallableStatement statement = con.prepareCall("exec res_doctor_PayBill ?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, p_id);
            statement.execute();
            Toast.makeText(this,"Thanh toán thành công!",Toast.LENGTH_LONG).show();
            Intent mainmenu = new Intent(this, DoctorMenuActivity.class);
            startActivity(mainmenu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void phoneCall(View view) {

    }

    public void Gmail(View view) {

    }
}
