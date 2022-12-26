package pet.project.spark.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.util.db.Database;
import redis.clients.jedis.JedisPool;

public class Redis {
    private static Logger LOG = LoggerFactory.getLogger(Database.class);

    private JedisPool pool;
}
