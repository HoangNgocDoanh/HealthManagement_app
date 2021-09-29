package com.cuoiky.smartdoctor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditProfileActivity extends AppCompatActivity {

    Intent intent = getIntent();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferencesEditor;

    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();

    private TextView fullName, phoneNumber, DOB, email, city, state, street, country;
    CallableStatement statement;
    Statement getStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        DOB = findViewById(R.id.txtage_doctor);
        email = findViewById(R.id.email);

        city = findViewById(R.id.patient_city);
        state = findViewById(R.id.patient_state);
        street = findViewById(R.id.patient_street);
        country = findViewById(R.id.patient_country);

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);


        //Fill data len activity
        getProfile();
    }

    public void SaveInfo(View view) {
        String ID_USER = "";
        if (intent != null) {
            ID_USER = intent.getStringExtra("ID_USER");
        }
        else {
            ID_USER = sharedPreferences.getString("ID_USER","");
        }
        try {
            statement = con.prepareCall("exec res_patient_SaveChange ?,?,?,?,?,?,?,?,?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,fullName.getText().toString());
            statement.setString(2,phoneNumber.getText().toString());
            statement.setString(3,DOB.getText().toString());
            statement.setString(4,email.getText().toString());

            statement.setString(5,city.getText().toString());
            statement.setString(6,state.getText().toString());
            statement.setString(7,street.getText().toString());
            statement.setString(8,country.getText().toString());
            statement.setString(9,ID_USER);

            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (!statement.execute()) {
                Toast.makeText(this,"Save successfully!",Toast.LENGTH_LONG).show();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getProfile() {
        String ID_USER = "";
        if (intent != null) {
            ID_USER = intent.getStringExtra("ID_USER");
        }
        else {
            ID_USER = sharedPreferences.getString("ID_USER","");
        }
        try {
            String sql = "select * from res_patient_GetById("+ ID_USER +")";
            getStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = getStatement.executeQuery(sql);
            if (resultSet.first()) {
                fullName.setText(resultSet.getString("fullName") != null ? resultSet.getString("fullName") : "Full Name");
                phoneNumber.setText(resultSet.getString("phoneNumber") != null ? resultSet.getString("phoneNumber") : "Phone Number");
                DOB.setText(resultSet.getString("DOB") != null ? resultSet.getString("DOB") : "Date Of Birth");
                email.setText(resultSet.getString("email") != null ? resultSet.getString("email") : "Email");

                city.setText(resultSet.getString("city") != null ? resultSet.getString("city") : "City");
                state.setText(resultSet.getString("state") != null ? resultSet.getString("state") : "State");
                street.setText(resultSet.getString("street") != null ? resultSet.getString("street") : "Street");
                country.setText(resultSet.getString("country") != null ? resultSet.getString("country") : "Country");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
