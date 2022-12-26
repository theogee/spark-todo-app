package pet.project.spark.model.config.spark;

import com.google.gson.annotations.SerializedName;

public class SparkConfig {
    @SerializedName("spark_port") private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
