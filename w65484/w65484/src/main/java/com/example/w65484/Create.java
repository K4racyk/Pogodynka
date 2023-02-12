package com.example.w65484;

import java.sql.Connection;
import java.sql.Statement;

public class Create {
    public static void createTable(){
        try{
            Connection c = DbAccessor.c;
            Statement stmt = c.createStatement();
            String query = "CREATE TABLE Odczyt(ID INT PRIMARY KEY NOT NULL, data TEXT NOT NULL, temperatura REAL NOT NULL, cisnienie REAL NOT NULL, wiatr REAL NOT NULL)";
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            System.out.println("Table created");
        }
        catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
