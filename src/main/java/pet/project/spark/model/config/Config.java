package pet.project.spark.model.config;

import com.google.gson.annotations.SerializedName;
import pet.project.spark.model.config.db.DatabaseConfig;
import pet.project.spark.model.config.redis.RedisConfig;
import pet.project.spark.model.config.session.SessionConfig;
import pet.project.spark.model.config.spark.SparkConfig;

public class Config {
    @SerializedName("spark_config") private SparkConfig sparkConfig;
    @SerializedName("db_config") private DatabaseConfig dbConfig;
    @SerializedName("redis_config") private RedisConfig redisConfig;
    @SerializedName("session_config") private SessionConfig sessionConfig;

    public Config(SparkConfig sparkConfig, DatabaseConfig dbConfig, RedisConfig redisConfig, SessionConfig sessionConfig) {
        this.sparkConfig = sparkConfig;
        this.dbConfig = dbConfig;
        this.redisConfig = redisConfig;
        this.sessionConfig = sessionConfig;
    }

    public DatabaseConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    public SessionConfig getSessionConfig() {
        return sessionConfig;
    }

    public void setSessionConfig(SessionConfig sessionConfig) {
        this.sessionConfig = sessionConfig;
    }

    public SparkConfig getSparkConfig() {
        return sparkConfig;
    }

    public void setSparkConfig(SparkConfig sparkConfig) {
        this.sparkConfig = sparkConfig;
    }
}
