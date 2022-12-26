package pet.project.spark.model.http.task;

import com.google.gson.annotations.SerializedName;

public class CreateTaskRequest {
    @SerializedName("task") private String task;
    private int userID; // injected from middleware

    public CreateTaskRequest(String task, int userID) {
        this.task = task;
        this.userID = userID;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
