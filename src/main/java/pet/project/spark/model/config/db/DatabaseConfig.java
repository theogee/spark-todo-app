package pet.project.spark.model.config.db;

import com.google.gson.annotations.SerializedName;

public class DatabaseConfig {
    @SerializedName("db_user") private String user;
    @SerializedName("db_password") private String password;
    @SerializedName("db_name") private String database;
    @SerializedName("db_host") private String host;

    public DatabaseConfig(String user, String password, String database, String host) {
        this.user = user;
        this.password = password;
        this.database = database;
        this.host = host;
    }

    public String getConnectionURI() {
        return String.format("jdbc:postgresql://%s/%s?user=%s&password=%s", this.host, this.database, this.user, this.password);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
