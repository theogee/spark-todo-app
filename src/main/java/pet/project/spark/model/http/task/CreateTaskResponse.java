package pet.project.spark.model.http.task;

import com.google.gson.annotations.SerializedName;

public class CreateTaskResponse {
    @SerializedName("success") private boolean success;
    @SerializedName("message") private String message;
    @SerializedName("error") private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
