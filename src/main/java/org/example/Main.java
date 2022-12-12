package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

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

        HashMap<String, Pair<String, Integer>> dataMatrix = new HashMap<>();

        for (Entity entity : entities) {
            String resource = entity.getResourceId();
            String job = entity.getJobId();
            Integer cost = entity.getCost();

            Pair pair = new Pair<>(job, cost);
            dataMatrix.put(resource, pair);
        }

        for (Map.Entry<String, Pair<String, Integer>> data : dataMatrix.entrySet()) {
            Pair pair = data.getValue();
            System.out.println(data.getKey() + "\t" + pair.getKey() + "\t" + pair.getValue());
        }
    }
}