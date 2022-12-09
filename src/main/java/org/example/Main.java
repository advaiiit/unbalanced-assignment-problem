package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        // Database Connection
        String dbDriver = "org.postgresql.Driver";
        String dbName = "jdbc:postgresql://localhost:5432/TestDB";
        String username = "postgres";
        String password = "changeme";

        ResultSet rs = null;
        ArrayList<Entity> entities = new ArrayList<>();
        Set<String> uniqueResources = new HashSet<>();
        Set<String> uniqueJobs = new HashSet<>();

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
                String currentResource = rs.getString("resource_id");
                String currentJob = rs.getString("job_id");
                int currentCost = rs.getInt("cost");

                uniqueResources.add(currentResource);
                uniqueJobs.add(currentJob);

                Entity entity = new Entity();
                entity.setResourceId(currentResource);
                entity.setJobId(currentJob);
                entity.setCost(currentCost);
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

        System.out.println();

        System.out.println("Unique Patients: " + uniqueResources.size());
        System.out.println("Unique Slots: " + uniqueJobs.size());
    }
}