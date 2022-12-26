package pet.project.spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.Config;
import pet.project.spark.model.config.db.DatabaseConfig;
import pet.project.spark.model.config.redis.RedisConfig;
import pet.project.spark.model.config.session.SessionConfig;
import pet.project.spark.util.db.Database;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

public class Main {
    private static Logger LOG = LoggerFactory.getLogger(Main.class);

    // TODO: inject from config
    private static final int SPARK_PORT = 8080;
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "root";
    private static final String DB_NAME = "spark-todo-app";
    private static final String DB_HOST = "localhost";
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final long SESSION_TTL = 60; // seconds
    private static final String SESSION_PREFIX = "session_id:";
    private static final String COOKIE_NAME = "todo.sid";

    public static void main(String[] args) {
        try {

            // load config
            DatabaseConfig dbConfig = new DatabaseConfig(DB_USER, DB_PASSWORD, DB_NAME, DB_HOST);
            RedisConfig redisConfig = new RedisConfig(REDIS_HOST, REDIS_PORT);
            SessionConfig sessionConfig = new SessionConfig(SESSION_TTL, SESSION_PREFIX, COOKIE_NAME);
            Config config = new Config(dbConfig, redisConfig, sessionConfig);

            // database init
            Database db = postgresInit(config);
            // redis init
            JedisPool redisPool = redisInit(config);

            // start app
            App app = new App(config, db, redisPool);
            app.init();

        } catch (Exception e) {
            LOG.error("internal server error. err: " + e);
            System.exit(1);
        }
    }

    static Database postgresInit(Config config) throws java.sql.SQLException {
        try {
            Database db = new Database(config.getDbConfig());
            db.connect();
            return db;
        } catch (java.sql.SQLException e) {
            LOG.error("error establishing connection to database. err: " + e);
            throw e;
        }
    }

    static JedisPool redisInit(Config config) throws Exception {
        try {
            JedisPool pool = new JedisPool(config.getRedisConfig().getHost(), config.getRedisConfig().getPort());
            Jedis jedis = pool.getResource();
            String ping = jedis.ping();
            LOG.info("pinging redis. response: " + ping);
            jedis.close();
            return pool;
        } catch (Exception e) {
            LOG.error("error establishing connection to redis. err: " + e);
            throw e;
        }
    }
}
