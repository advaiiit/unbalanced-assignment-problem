package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Main {

    public static void printEntity(ArrayList<Entity> entities) {
        for (Entity entity : entities) {
            System.out.println(entity.getResourceId() + "\t" + entity.getJobId() + "\t" + entity.getCost());
        }
    }

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

                Entity entity = new Entity(currentResource, currentJob, currentCost);
                entities.add(entity);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        int maxResources = uniqueResources.size();
        int maxJobs = uniqueJobs.size();

        if (maxResources != maxJobs) {
            if (maxResources > maxJobs) {
                int generateJobs = maxResources - maxJobs;

                for (String resource : uniqueResources) {
                    for (int i = 0; i < generateJobs; i++) {
                        Entity entity = new Entity(resource, "D" + i, 0);
                        uniqueJobs.add(entity.getJobId());
                        entities.add(entity);
                    }
                }
            } else {
                int generateResources = maxJobs - maxResources;

                for (String job : uniqueJobs) {
                    for (int i = 0; i < generateResources; i++) {
                        Entity entity = new Entity("D" + i, job, 0);
                        uniqueResources.add(entity.getResourceId());
                        entities.add(entity);
                    }
                }
            }
        }

        maxResources = uniqueResources.size();
        maxJobs = uniqueJobs.size();

        HungarianAlgorithm hungarianAlgorithm = new HungarianAlgorithm(entities, uniqueResources, uniqueJobs);
        ArrayList<Entity> reducesEntities = hungarianAlgorithm.reduceMatrix();

        printEntity(reducesEntities);
    }
}