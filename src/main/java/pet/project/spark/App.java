package pet.project.spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.handler.http.HttpHandler;
import pet.project.spark.model.config.Config;
import pet.project.spark.repo.todo.TodoRepo;
import pet.project.spark.usecase.todo.TodoUsecase;
import pet.project.spark.util.db.Database;
import pet.project.spark.util.session.SessionManager;
import redis.clients.jedis.JedisPool;

import static spark.Spark.port;

public class App {
    private Logger LOG = LoggerFactory.getLogger(App.class);
    private Database db;
    private JedisPool redisPool;
    private Config config;

    public App(Config config, Database db, JedisPool redisPool) {
        this.config = config;
        this.db = db;
        this.redisPool = redisPool;
    }
    public void init() {
        TodoRepo todoRepo = new TodoRepo(this.config, this.db);
        TodoUsecase todoUsecase = new TodoUsecase(this.config, todoRepo);
        SessionManager sessionManager = new SessionManager(this.config, redisPool);
        HttpHandler httpHandler = new HttpHandler(this.config, todoUsecase, sessionManager);

        Router router = new Router(this.config, httpHandler);

        port(config.getSparkConfig().getPort());
        router.register();
    }
}
