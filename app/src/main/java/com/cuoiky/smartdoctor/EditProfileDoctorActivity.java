package com.cuoiky.smartdoctor;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class EditProfileDoctorActivity extends AppCompatActivity {
    Intent intent = getIntent();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferencesEditor;
    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();
    EditText fullName, gender, birthday, age, email, fauculty;
    EditText stress, state, city, country;
    CallableStatement statement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_doctor);

        fullName = findViewById(R.id.txtfullname_doctor);
        gender = findViewById(R.id.txtgender_doctor);
        birthday = findViewById(R.id.txtbirthday_doctor);
        email = findViewById(R.id.txtemail_doctor);
        fauculty = findViewById(R.id.txtfauculty);
        // Address
        stress = findViewById(R.id.txtstress_doctor);
        state = findViewById(R.id.txtstate_doctor);
        city = findViewById(R.id.txtcity_doctor);
        country = findViewById(R.id.txtcity_doctor);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
    }

    public void SaveInfo(View view) {
        Date now = new Date();
        int nowYear = now.getYear() + 1900;
        int age = Math.abs(Integer.valueOf(birthday.getText().toString()) - nowYear);
        String ID_USER = "";
        if (intent != null) {
            ID_USER = intent.getStringExtra("ID_USER");
        }
        else {
            ID_USER = sharedPreferences.getString("ID_USER","");
        }
        Address();
        Fauculty();
        try {
            statement = con.prepareCall("exec res_doctor_SaveChange ?,?,?,?,?,?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, fullName.getText().toString());
            statement.setString(2, gender.getText().toString());
            statement.setString(3, birthday.getText().toString());
            statement.setString(4, String.valueOf(age));
            statement.setString(5, email.getText().toString());
            statement.setString(6,ID_USER);
            statement.execute();
            Toast.makeText(this,"Update Thành Công",Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Address() {
        try {
            statement = con.prepareCall("exec res_add_adrees ?,?,?,?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, stress.getText().toString());
            statement.setString(2, state.getText().toString());
            statement.setString(3, city.getText().toString());
            statement.setString(4, country.toString());
            statement.execute();
        } catch (
                SQLException e) {
        }
    }

    public void Fauculty() {
        try {
            statement = con.prepareCall("exec res_add_fauculty ?,?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, fauculty.getText().toString());
            statement.setString(2, "Active");
            statement.execute();
        } catch (
                SQLException e) {
        }
    }
}
