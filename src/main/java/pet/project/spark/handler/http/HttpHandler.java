package pet.project.spark.handler.http;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.model.config.Config;
import pet.project.spark.model.response.http.auth.LoginRequest;
import pet.project.spark.model.response.http.auth.LoginResponse;
import pet.project.spark.model.response.http.User;
import pet.project.spark.model.response.http.auth.LogoutResponse;
import pet.project.spark.usecase.todo.TodoUsecase;
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
}
