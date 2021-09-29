package com.cuoiky.smartdoctor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AddMedicalActivity extends AppCompatActivity {
    Intent intent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferencesEditor;
    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();
    EditText Symptom,Date,Threated;
    CallableStatement statement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical);

        intent = getIntent();

        Symptom = findViewById(R.id.symptom);
        Date = findViewById(R.id.date);
        Threated = findViewById(R.id.threated);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addDisease(View view) {
        String Disease = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                String ID_PATIENTS = "";
        if (intent != null) {
            ID_PATIENTS = intent.getStringExtra("ID_PATIENTS");
        }
        else {
            ID_PATIENTS = sharedPreferences.getString("ID_PATIENTS","");
        }
        try {
            statement = con.prepareCall("exec res_patients_Adddissease ?,?,?,?,?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, ID_PATIENTS);
            statement.setString(2, Symptom.getText().toString());
            statement.setString(3, Threated.getText().toString());
            statement.setString(4, Date.getText().toString());
            statement.setString(5, Disease);
            statement.execute();
            Toast.makeText(this,"Đã cập nhật hồ sơ bệnh án!",Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
