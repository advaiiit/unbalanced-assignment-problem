package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Database Connection
        String dbDriver = "org.postgresql.Driver";
        String dbName = "jdbc:postgresql://localhost:5432/TestDB";
        String username = "postgres";
        String password = "changeme";

        ResultSet rs = null;
        ArrayList<Entity> entities = new ArrayList<>();

        try {
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, username, password);
            Statement statement = conn.createStatement();
            rs = statement.executeQuery("Select resource_id, job_id, cost from public.bb");
        } catch(Exception e) {
            System.out.println(e);
        }

        try {
            while(rs.next()) {
                Entity entity = new Entity();
                entity.setResourceId(rs.getString("resource_id"));
                entity.setJobId(rs.getString("job_id"));
                entity.setCost(rs.getInt("cost"));
                entities.add(entity);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        for (Entity entity : entities) {
            System.out.print("resource_id: " + entity.getResourceId());
            System.out.print("\tjob_id: " + entity.getJobId());
            System.out.print("\tcost: " + entity.getCost());
            System.out.println();
        }
    }
}