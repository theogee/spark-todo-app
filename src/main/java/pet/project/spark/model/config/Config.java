package pet.project.spark.model.config;

import pet.project.spark.model.config.db.DatabaseConfig;
import pet.project.spark.model.config.redis.RedisConfig;
import pet.project.spark.model.config.session.SessionConfig;
import pet.project.spark.util.redis.Redis;

public class Config {
    private DatabaseConfig dbConfig;
    private RedisConfig redisConfig;

    private SessionConfig sessionConfig;

    public Config(DatabaseConfig dbConfig, RedisConfig redisConfig, SessionConfig sessionConfig) {
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
}
