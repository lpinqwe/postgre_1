package com.example.postgre_1;

import java.sql.*;

public class PostgreSend {

    private final String url = "jdbc:postgresql://192.168.99.106:5432/postgres";
    private final String user = "postgres";
    private final String password = "password";
    private String SQLstring ="insert into newdata\n" +
            "(mac, data1, data2, data3,\"date\")\n" +
            "values\n" +
            "('ac:ac:ac',0.11,'data2','data3',now())\n" +
            ";";

    public String getSQLstring() {
        return SQLstring;
    }

    public PostgreSend setSQLstring(String SQLstring) {
        this.SQLstring = SQLstring;
        return this;
    }

    public Connection connect() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return conn;
    }

    public String CreateString(String[] data) {
        String newString = "";
        for (int i = 0; i < data.length; i++) {
            newString += data[i];
        }
        return  newString;
    }
    public String send(String strToSend){
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQLstring)) {
            ResultSet rs = pstmt.executeQuery();
            System.out.println("sended: " + strToSend);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return strToSend;
    }
    public void sendData(String dataToSend){
        this.send(dataToSend);

    }
}
