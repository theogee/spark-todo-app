package pet.project.spark.util.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.db.DatabaseConfig;
import java.sql.*;

public class Database {
    private static Logger LOG = LoggerFactory.getLogger(Database.class);
    private DatabaseConfig conf;

    private Connection conn;

    public Database(DatabaseConfig conf) {
        this.conf = conf;
    }

    public void connect() throws java.sql.SQLException {
        try {
            String url = conf.getConnectionURI();
            conn = DriverManager.getConnection(url);
        } catch (java.sql.SQLException e) {
            LOG.error("error establishing database connection. err: " + e);
            throw e;
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
