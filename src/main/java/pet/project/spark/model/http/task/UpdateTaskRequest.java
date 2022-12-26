package pet.project.spark.model.http.task;

import com.google.gson.annotations.SerializedName;

public class UpdateTaskRequest {
    private int userID; // injected from middleware
    private int taskID; // injected from route params
    @SerializedName("new_task") private String newTask;

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getNewTask() {
        return newTask;
    }

    public void setNewTask(String newTask) {
        this.newTask = newTask;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
