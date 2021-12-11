package com.user.goservice;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Task extends AsyncTask<Void, Void, Void> {

    public  String records = "", error = "";
    public  String query = "";
    public String retrieve = "get", update = "set";
    public String queryType = retrieve;
    public  int flag = 0;

    public void setQuery(String query, String queryType) {
        this.query = query;
        this.queryType = queryType;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.43.8:3306/goservicedb", "admin", "1234");
            Statement statement = connection.createStatement();
            if (queryType.equals(update))
                flag = statement.executeUpdate(query);
            else if (queryType.equals(retrieve)) {
                statement.executeQuery(query);
            }


        } catch (Exception e) {
            Log.e("Error", e.getLocalizedMessage());
        }

        return null;
    }
}
