package org.example;

import java.util.*;

public class HungarianAlgorithm {

    ArrayList<Entity> entities;
    Set<String> uniqueResources;
    Set<String> uniqueJobs;

    public HungarianAlgorithm(ArrayList<Entity> entities, Set<String> uniqueResources, Set<String> uniqueJobs) {
        this.entities = entities;
        this.uniqueResources = uniqueResources;
        this.uniqueJobs = uniqueJobs;
    }

    public ArrayList<Entity> reduceMatrix() {
        // Row Reduction
        for (String resource : uniqueResources) {
            int currentRowMin = Integer.MAX_VALUE;
            for (Entity entity : entities) {
                if (Objects.equals(entity.getResourceId(), resource) && entity.getCost() < currentRowMin) {
                    currentRowMin = entity.getCost();
                }
            }

            for (Entity entity : entities) {
                if (Objects.equals(entity.getResourceId(), resource)) {
                    int currentCost = entity.getCost();
                    entity.setCost(currentCost - currentRowMin);

                }
            }
        }

        // Column Reduction
        for (String job : uniqueJobs) {
            int currentColMin = Integer.MAX_VALUE;
            for (Entity entity : entities) {
                if (Objects.equals(entity.getJobId(), job) && entity.getCost() < currentColMin) {
                    currentColMin = entity.getCost();
                }
            }

            for (Entity entity : entities) {
                if (Objects.equals(entity.getJobId(), job)) {
                    int currentCost = entity.getCost();
                    entity.setCost(currentCost - currentColMin);
                }
            }
        }

        return entities;
    }
}
