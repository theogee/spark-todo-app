package pet.project.spark.usecase.todo;

import com.sun.jdi.event.ExceptionEvent;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.Task;
import pet.project.spark.model.config.Config;
import pet.project.spark.model.http.auth.LoginRequest;
import pet.project.spark.model.User;
import pet.project.spark.model.http.auth.RegisterRequest;
import pet.project.spark.model.http.task.CreateTaskRequest;
import pet.project.spark.model.http.task.DeleteTaskRequest;
import pet.project.spark.model.http.task.UpdateTaskRequest;
import pet.project.spark.repo.todo.TodoRepo;

import java.util.ArrayList;

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

            if (!BCrypt.checkpw(data.getPassword(), user.getPassword())) {
                LOG.error("incorrect password for login attempt with username: " + user.getUsername());
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

    public ArrayList<Task> getTaskList(int userID) throws Exception {
        try {
            return todoRepo.getTaskList(userID);
        } catch (Exception e) {
            LOG.error("error calling todoRepo.getTaskList. err: " + e);
            throw e;
        }
    }

    public void createTask(CreateTaskRequest data) throws Exception {
        try {
            todoRepo.createTask(data.getUserID(), data.getTask());
        } catch (Exception e) {
            LOG.error("error calling todoRepo.createTask. err: " + e);
            throw e;
        }
    }

    public boolean updateTask(UpdateTaskRequest data) throws Exception {
        try {
            ArrayList<Task> tasks = todoRepo.getTaskList(data.getUserID());
            for (Task t : tasks) {
                if (t.getTaskID() == data.getTaskID()) {
                   todoRepo.updateTask(data.getTaskID(), data.getNewTask());
                   return true;
                }
            }
            LOG.error("taskID: " + data.getTaskID() + " not found for userID: " + data.getUserID());
            return false;
        } catch (Exception e) {
            LOG.error(String.format("error updating taskID: %d for userID: %d. err: %s", data.getTaskID(), data.getUserID(), e));
            throw e;
        }
    }

    public boolean deleteTask(DeleteTaskRequest data) throws Exception {
        try {
            ArrayList<Task> tasks = todoRepo.getTaskList(data.getUserID());
            for (Task t : tasks) {
                if (t.getTaskID() == data.getTaskID()) {
                    todoRepo.deleteTask(data.getTaskID());
                    return true;
                }
            }
            LOG.error("taskID: " + data.getTaskID() + " not found for userID: " + data.getUserID());
            return false;
        } catch (Exception e) {
            LOG.error(String.format("error deleting taskID: %d for userID: %d. err: %s", data.getTaskID(), data.getUserID(), e));
            throw e;
        }
    }
}
