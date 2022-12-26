package pet.project.spark.repo.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.Config;
import pet.project.spark.model.response.http.User;
import pet.project.spark.util.db.Database;
import redis.clients.jedis.JedisPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class TodoRepo {
    private static Logger LOG = LoggerFactory.getLogger(TodoRepo.class);
    private Database db;
    private Config config;

    public TodoRepo(Config config, Database db) {
        this.config = config;
        this.db = db;
    }

    public User getUserData(String username) throws Exception {
        User user = new User();
        try {
            PreparedStatement st = db.getConn().prepareStatement(Query.getUserDataQuery);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                user.setUserID(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
            }

            rs.close();
            st.close();
        } catch (java.sql.SQLException e) {
            LOG.error("error query db. err: " + e);
        }

        return user;
    }
}
