package pet.project.spark.model.http.task;

import com.google.gson.annotations.SerializedName;
import pet.project.spark.model.Task;

import java.util.ArrayList;

public class GetTaskListResponse {
    @SerializedName("success") private boolean success;
    @SerializedName("data") private ArrayList<Task> data;
    @SerializedName("error") private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Task> getData() {
        return data;
    }

    public void setData(ArrayList<Task> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
