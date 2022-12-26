package pet.project.spark.repo.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.Config;
import pet.project.spark.model.User;
import pet.project.spark.util.db.Database;

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

    public void registerUser(String username, String password) throws Exception {
        try {
            PreparedStatement st = db.getConn().prepareStatement(Query.registerUser);
            st.setString(1, username);
            st.setString(2, password);
            st.executeUpdate();
            st.close();
            LOG.info("user registered successfully. username: " + username);
        } catch (java.sql.SQLException e) {
            LOG.error("error insert user to db. err: " + e);
            throw e;
        }
    }

    public void createTask(int userID, String task) throws Exception {
        try {
            PreparedStatement st = db.getConn().prepareStatement(Query.createTask);
            st.setInt(1, userID);
            st.setString(2, task);
            st.executeUpdate();
            st.close();
            LOG.info("task created successfully. userID: " + userID);
        } catch (java.sql.SQLException e) {
            LOG.error("error insert task to db. err: " + e);
            throw e;
        }
    }
}
