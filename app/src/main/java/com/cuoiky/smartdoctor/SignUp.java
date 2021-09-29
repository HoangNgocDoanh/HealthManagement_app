package com.cuoiky.smartdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUp extends AppCompatActivity {

    TextView Loginhere;
    EditText username,password;
    Button btnsignup;
    RadioButton rddoctor, rdpatient;

    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Loginhere = findViewById(R.id.txtLoginhere);
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        btnsignup = findViewById(R.id.btnSignup);
        rddoctor = findViewById(R.id.rdDoctor);
        rdpatient = findViewById(R.id.rdPatients);
    }

    public void SignUp (View view) {
        try {
            String uname = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String isdoctor;
            if (rddoctor.isChecked())
            {
                isdoctor = "1";
            }
            else {
                isdoctor = "0";
            }
            if (!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(pass)) {
                CallableStatement statement = con.prepareCall("exec res_user_sign_up ?,?,?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, uname);
                statement.setString(2, pass);
                statement.setString(3,isdoctor);
                statement.execute();

                Toast.makeText(this,"Đăng ký thành công!",Toast.LENGTH_LONG).show();

                Intent mainmenu = new Intent(this, Login.class);
                startActivity(mainmenu);
            }
            else {
                Toast.makeText(this,"Vui lòng nhập thông tin đăng ký!",Toast.LENGTH_LONG).show();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            Toast.makeText(this,"Đăng ký thất bại!",Toast.LENGTH_LONG).show();
        }
    }
}