package pet.project.spark.util.session;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.Config;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SessionManager {
    private Logger LOG = LoggerFactory.getLogger(SessionManager.class);
    private JedisPool redisPool;

    private Config config;

    public SessionManager(Config config, JedisPool redisPool) {
        this.redisPool = redisPool;
        this.config = config;
    }

    public void setSession(String key, String value) throws Exception {
        try (Jedis jedis = redisPool.getResource()) {
            String keyPrefixed = config.getSessionConfig().getPrefix() + key;
            jedis.setex(keyPrefixed, config.getSessionConfig().getTtl(), value);
        } catch (Exception e) {
            LOG.error("error setting session to redis. err: " + e);
            throw e;
        }
    }

    public void destroySession(String key) throws Exception {
        String keyPrefixed = config.getSessionConfig().getPrefix() + key;
        try (Jedis jedis = redisPool.getResource()) {
            jedis.del(keyPrefixed);
        } catch (Exception e) {
            LOG.error("error deleting session from redis. key: " + keyPrefixed + " err: " + e);
            throw e;
        }
    }

    public int getUserIDFromSession(String key) throws Exception {
        String keyPrefixed = config.getSessionConfig().getPrefix() + key;
        try (Jedis jedis = redisPool.getResource()) {
            String userID = jedis.get(keyPrefixed);
            if (userID == null) return -1;
            return Integer.parseInt(userID);
        } catch (Exception e) {
            LOG.error("error getting userID from session. key: " +  keyPrefixed + " err: " + e);
            throw e;
        }
    }
}
