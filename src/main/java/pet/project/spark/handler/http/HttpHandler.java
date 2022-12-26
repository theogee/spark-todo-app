package pet.project.spark.handler.http;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.Config;
import pet.project.spark.model.http.auth.*;
import pet.project.spark.model.User;
import pet.project.spark.model.http.task.CreateTaskRequest;
import pet.project.spark.model.http.task.CreateTaskResponse;
import pet.project.spark.usecase.todo.TodoUsecase;
import pet.project.spark.util.constant.Constant;
import pet.project.spark.util.session.SessionManager;
import spark.Request;
import spark.Response;

import pet.project.spark.util.response.ResponseManager;

public class HttpHandler {
    private static Logger LOG = LoggerFactory.getLogger(HttpHandler.class);
    private TodoUsecase todoUsecase;
    private SessionManager sessionManager;
    private Config config;
    private final Gson gson = new Gson();

    public HttpHandler(Config config, TodoUsecase todoUsecase, SessionManager sessionManager) {
        this.config = config;
        this.todoUsecase = todoUsecase;
        this.sessionManager = sessionManager;
    }

    public int authorizeMW(Request req, Response res) throws Exception {
        try {
            String sessionKey = req.cookie(config.getSessionConfig().getCookieName());
            return sessionManager.getUserIDFromSession(sessionKey);
        } catch (Exception e) {
            LOG.error("error calling sessionManager.getUserIDFromSession. err: " + e);
            throw e;
        }
    }

    public RegisterResponse register(Request req, Response res) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            RegisterRequest data = gson.fromJson(req.body(), RegisterRequest.class);
            if (data.getUsername().equals("")) {
                registerResponse.setSuccess(false);
                registerResponse.setError("missing username");
                ResponseManager.setHeaderJSON(400, res);
                return registerResponse;
            }

            if (data.getUsername().length() > 11) {
                registerResponse.setSuccess(false);
                registerResponse.setError("username can't be longer than 11 characters");
                ResponseManager.setHeaderJSON(400, res);
                return registerResponse;
            }

            if (data.getPassword().equals("")) {
                registerResponse.setSuccess(false);
                registerResponse.setError("missing password");
                ResponseManager.setHeaderJSON(400, res);
                return registerResponse;
            }

            boolean isSuccess = todoUsecase.registerUser(data);

            if (!isSuccess) {
                registerResponse.setSuccess(false);
                registerResponse.setError("username already exist. please try with a different username");
                ResponseManager.setHeaderJSON(400, res);
                return registerResponse;
            }

            registerResponse.setSuccess(true);
            registerResponse.setMessage("username: " + data.getUsername() + " registered successfully");
            ResponseManager.setHeaderJSON(201, res);
            return registerResponse;
        } catch (Exception e) {
            LOG.error("error calling todoUsecase.registerUser. err: " + e);
            registerResponse.setSuccess(false);
            registerResponse.setError(e.toString());
            ResponseManager.setHeaderJSON(500, res);
            return registerResponse;
        }
    }

    public LoginResponse login(Request req, Response res) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            LoginRequest data = gson.fromJson(req.body(), LoginRequest.class);

            // input validation
            if (data.getUsername().equals("")) {
                loginResponse.setSuccess(false);
                loginResponse.setError("missing username");

                ResponseManager.setHeaderJSON(400, res);
                return loginResponse;
            }

            if (data.getPassword().equals("")) {
                loginResponse.setSuccess(false);
                loginResponse.setError("missing password");

                ResponseManager.setHeaderJSON(400, res);
                return loginResponse;
            }

            // fetch user data
            User user = todoUsecase.login(data);
            if (user.getUserID() == 0) {
                loginResponse.setSuccess(false);
                loginResponse.setError("incorrect username or password");

                ResponseManager.setHeaderJSON(400, res);
                return loginResponse;
            }

            // set session
            String sessionID = req.session().id();
            sessionManager.setSession(sessionID, Integer.toString(user.getUserID()));
            res.cookie("todo.sid", sessionID, (int) config.getSessionConfig().getTtl());
            loginResponse.setSuccess(true);
            loginResponse.setMessage("login successful");

            ResponseManager.setHeaderJSON(200, res);

            return loginResponse;
        } catch (com.google.gson.JsonSyntaxException e) {
            LOG.error("error parsing JSON request. err: " + e);
            loginResponse.setSuccess(false);
            loginResponse.setError(e.toString());

            ResponseManager.setHeaderJSON(500, res);
            return loginResponse;
        } catch (Exception e) {
            LOG.error("error calling todoUsecase.login. err: " + e);
            loginResponse.setSuccess(false);
            loginResponse.setError(e.toString());

            ResponseManager.setHeaderJSON(500, res);
            return loginResponse;
        }
    }

    public LogoutResponse logout(Request req, Response res) {
        LogoutResponse logoutResponse = new LogoutResponse();
        String sessionKey = req.cookie(config.getSessionConfig().getCookieName());
        try {
            sessionManager.destroySession(sessionKey);
            logoutResponse.setSuccess(true);
            logoutResponse.setMessage("logged out successfully");
            ResponseManager.setHeaderJSON(200, res);
            return logoutResponse;
        } catch (Exception e) {
            LOG.error("error calling sessionManager.destroySession. sessionKey: " + sessionKey + " err: " + e);
            logoutResponse.setSuccess(false);
            logoutResponse.setError(e.toString());
            ResponseManager.setHeaderJSON(500, res);
            return logoutResponse;
        }
    }

    public String getTaskList(Request req, Response res) {
        String userID = req.attribute("user.id");
        LOG.info("userID: " + userID);
        return "get task list";
    }

    public CreateTaskResponse createTask(Request req, Response res) {
        CreateTaskResponse createTaskResponse = new CreateTaskResponse();
        try {
            CreateTaskRequest data = gson.fromJson(req.body(), CreateTaskRequest.class);
            if (data.getTask().equals("")) {
                createTaskResponse.setSuccess(false);
                createTaskResponse.setError("task can't be empty");
                ResponseManager.setHeaderJSON(400, res);
                return createTaskResponse;
            }
            // inject userID from middleware
            String userID = req.attribute(Constant.USER_ID_MW);
            data.setUserID(Integer.parseInt(userID));

            todoUsecase.createTask(data);
            createTaskResponse.setSuccess(true);
            createTaskResponse.setMessage("task created successfully");
            ResponseManager.setHeaderJSON(201, res);
            return createTaskResponse;
        } catch (Exception e) {
            LOG.error("error calling todoUsecase.createTask. err: " + e);
            createTaskResponse.setSuccess(false);
            createTaskResponse.setError(e.toString());
            ResponseManager.setHeaderJSON(500, res);
            return createTaskResponse;
        }
    }
}
