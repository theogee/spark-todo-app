package pet.project.spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.Config;
import pet.project.spark.util.config.ConfigManager;
import pet.project.spark.util.db.Database;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            // load config
            Config config = ConfigManager.load("config/spark-todo-config.json");
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
