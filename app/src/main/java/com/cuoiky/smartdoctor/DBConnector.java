package com.cuoiky.smartdoctor;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static String IP = "192.168.43.148";
    private static String Port = "1433";
    private static String driver = "net.sourceforge.jtds.jdbc.Driver";
    private static String DBName = "APPSUCKHOE";
    private static String user = "android";
    private static String password = "123";
    private static String uri = "jdbc:jtds:sqlserver://" + IP + ";instance=SQLEXPRESS;user=" + user + ";password=" + password + ";databasename=" + DBName + ";";

    private Connection con = null;

    public DBConnector() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(uri);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    };
    public Connection getCon() {
        return con;
    }
}
