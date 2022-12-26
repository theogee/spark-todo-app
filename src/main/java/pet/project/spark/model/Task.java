package pet.project.spark.model;

import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("task_id") private int taskID;
    @SerializedName("task") private String task;

    public Task(int taskID, String task) {
        this.taskID = taskID;
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String message) {
        this.task = message;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
