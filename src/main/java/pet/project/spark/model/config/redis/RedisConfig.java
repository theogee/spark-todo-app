package pet.project.spark.model.config.redis;

import com.google.gson.annotations.SerializedName;

public class RedisConfig {
    @SerializedName("redis_host") private String host;
    @SerializedName("redis_port") private int port;

    public RedisConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
