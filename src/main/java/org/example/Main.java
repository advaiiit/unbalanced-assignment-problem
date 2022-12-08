package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        // Database Connection
        String dbDriver = "org.postgresql.Driver";
        String dbName = "jdbc:postgresql://localhost:5432/TestDB";
        String username = "postgres";
        String password = "changeme";

        ResultSet table = null;

        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, username, password);
            Statement statement = conn.createStatement();
            table = statement.executeQuery("Select resource_id, job_id, cost from public.bb");
        } catch(Exception e) {
            System.out.println(e);
        }

        try {
            while(table.next()) {
                System.out.print("Resource ID: " + table.getString(1));
                System.out.print("\tJob ID: " + table.getString(2));
                System.out.print("\tCost: " + table.getInt(3));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}