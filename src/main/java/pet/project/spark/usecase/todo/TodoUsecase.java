package pet.project.spark.usecase.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.Config;
import pet.project.spark.model.response.http.auth.LoginRequest;
import pet.project.spark.model.response.http.User;
import pet.project.spark.repo.todo.TodoRepo;

public class TodoUsecase {
    private static Logger LOG = LoggerFactory.getLogger(TodoUsecase.class);
    private TodoRepo todoRepo;
    private Config config;

    public TodoUsecase(Config config, TodoRepo todoRepo) {
        this.config = config;
        this.todoRepo = todoRepo;
    }

    public User login(LoginRequest data) throws Exception {
        User user = new User();
        try {
            user = todoRepo.getUserData(data.getUsername());
            if (user.getUserID() == 0) {
                LOG.error("user not found. username: " + user.getUsername());
                return user;
            }
            if (!user.getPassword().equals(data.getPassword())) {
                LOG.error("incorrect password");
                return new User();
            }
            return user;
        } catch (Exception e) {
            LOG.error("error calling todoRepo.getUserData. err: " + e);
            throw e;
        }
    }
}
