package com.cuoiky.smartdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText username,password;
    TextView SignupHere;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferencesEditor;
    Intent mainmenu;

    DBConnector dbConnector = new DBConnector();
    Connection con = dbConnector.getCon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        if (sharedPreferences.getBoolean("patient",false)) {
            mainmenu = new Intent(this, MainActivity.class);
            startActivity(mainmenu);
        }
        else if (sharedPreferences.getBoolean("doctor",false)) {
            mainmenu = new Intent(this, DoctorMenuActivity.class);
            startActivity(mainmenu);
        }
        SignupHere = findViewById(R.id.txtSignUphere);
        btnLogin = findViewById(R.id.btnSignup);
        username = findViewById(R.id.txtusername);
        password = findViewById(R.id.txtpassword);

        SignupHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void Login(View view) {
       try {
           String uname = username.getText().toString().trim();
           String pass = password.getText().toString().trim();

           if (!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(pass)) {
               CallableStatement statement = con.prepareCall("exec res_user_login ?,?",ResultSet.TYPE_SCROLL_INSENSITIVE,
                       ResultSet.CONCUR_READ_ONLY);
               statement.setString(1,uname);
               statement.setString(2,pass);
               statement.execute();
               ResultSet resultSet = statement.getResultSet();
               if (resultSet.first()) {
                    if (resultSet.getInt(1) != 0) {
                        preferencesEditor = sharedPreferences.edit();
                        Toast.makeText(this,"Đăng nhập thành công!",Toast.LENGTH_LONG).show();

                        //1: doctor   -    2:patient
                        if (resultSet.getInt(1) == 2 ) {
                            mainmenu = new Intent(this, MainActivity.class);
                            preferencesEditor.putBoolean("patient",true);
                        }
                        else {
                            mainmenu = new Intent(this, DoctorMenuActivity.class);
                            preferencesEditor.putBoolean("doctor",true);
                        }
                        mainmenu.putExtra("ID_USER",resultSet.getInt(2));

                        preferencesEditor.putString("ID_USER",String.valueOf(resultSet.getInt(2)));

                        preferencesEditor.commit();
                        startActivity(mainmenu);
                    }
                    else {
                        Toast.makeText(this,"Sai tên đăng nhập hoặc mật khẩu!",Toast.LENGTH_LONG).show();
                   }
               };
           }
           else {
               Toast.makeText(this,"Vui lòng nhập thông tin đăng nhập!",Toast.LENGTH_LONG).show();
           }
       }
       catch (SQLException e) {
           e.printStackTrace();
           Toast.makeText(this,"Đăng nhập thất bại!",Toast.LENGTH_LONG).show();
       }

    }
}