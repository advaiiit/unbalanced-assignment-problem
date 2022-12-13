

package org.example;

public class Entity {

    private String resourceId;
    private String jobId;
    private int cost;

    public Entity() {

    }

    public Entity(String resourceId, String jobId, int cost) {
        this.resourceId = resourceId;
        this.jobId = jobId;
        this.cost = cost;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
