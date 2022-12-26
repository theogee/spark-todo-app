package pet.project.spark.model.http.task;

public class DeleteTaskRequest {
    private int userID; // injected from middleware
    private int taskID; // injected from route params

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
