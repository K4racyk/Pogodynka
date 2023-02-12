package com.example.w65484;

import java.sql.*;

public class DbAccessor {
    static Connection c;
    public static Connection createConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Connection opened");
        }
        catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return c;
    }

    public static void closeConnection(){
        try{
            c.close();
        }
        catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
