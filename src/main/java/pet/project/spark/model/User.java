package pet.project.spark.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_id") private int userID;
    @SerializedName("username") private String username;
    @SerializedName("password") private String password;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
