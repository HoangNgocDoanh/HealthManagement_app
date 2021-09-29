package com.cuoiky.smartdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalFolderActivity extends AppCompatActivity {

    Intent intent;
    TextView disease,date,treated;

    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_folder);

        disease = findViewById(R.id.symptom);
        date = findViewById(R.id.date);
        treated = findViewById(R.id.threated);

        intent = getIntent();

        GetData();
    }

    public void GetData() {
        String patient_id = intent.getStringExtra("ID_USER");
        try {
            CallableStatement statement = con.prepareCall("exec res_patient_getDisease ?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,patient_id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.first()) {
                disease.setText(resultSet.getString("symptom") != null ? resultSet.getString("symptom") : "");
                date.setText(resultSet.getString("hospitalized_date") != null ? resultSet.getString("hospitalized_date") : "");
                treated.setText(resultSet.getString("treated") != null ? resultSet.getString("treated") : "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void back(View view) {
        finish();
    }
}
