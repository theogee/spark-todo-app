package pet.project.spark.usecase.todo;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.Config;
import pet.project.spark.model.response.http.auth.LoginRequest;
import pet.project.spark.model.response.http.User;
import pet.project.spark.model.response.http.auth.RegisterRequest;
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
        try {
            User user = todoRepo.getUserData(data.getUsername());
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

    public boolean registerUser(RegisterRequest data) throws Exception {
        try {
            User user = todoRepo.getUserData(data.getUsername());
            if (user.getUserID() != 0) {
                LOG.error("username already exist. username: " + user.getUsername());
                return false;
            }

            // username can be used
            String hashedPassword = BCrypt.hashpw(data.getPassword(), BCrypt.gensalt());
            todoRepo.registerUser(data.getUsername(), hashedPassword);
            return true;
        } catch (Exception e) {
            LOG.error("error registering user. err: " + e);
            throw e;
        }
    }
}
