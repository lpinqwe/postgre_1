package com.example.postgre_1;

import java.sql.*;

public class PostgreSend {

    private final String url = "jdbc:postgresql://192.168.99.106:5432/postgres";
    private final String user = "postgres";
    private final String password = "password";
    private String SQLstring ;

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

    public String send(String[] data) {
        String newString = "";
        for (int i = 0; i < data.length; i++) {
            newString += data[i];
        }
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQLstring)) {
            ResultSet rs = pstmt.executeQuery();
            System.out.println("sended: " + newString);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return newString;
    }
}
