package pet.project.spark;

import static spark.Spark.*;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.project.spark.handler.http.HttpHandler;
import pet.project.spark.model.config.Config;
import pet.project.spark.model.response.http.InternalServerErrorResponse;
import pet.project.spark.model.response.http.UnauthorizedResponse;
import pet.project.spark.util.response.ResponseManager;
import spark.HaltException;
import spark.Request;
import spark.Response;

import java.util.HashMap;


public class Router {
    private final static Logger LOG = LoggerFactory.getLogger(Router.class);
    private final HttpHandler httpHandler;
    private Config config;
    private final Gson gson = new Gson();
    public Router(Config config, HttpHandler httpHandler) {
        this.config = config;
        this.httpHandler = httpHandler;
    }

    public void register() {
        before("/task", this::authorize);
        before("/task/*", this::authorize);
        before("/logout", this::authorize);

        get("/task", httpHandler::getTaskList);

        post("/login", httpHandler::login, gson::toJson);
        post("/logout", httpHandler::logout, gson::toJson);

    }

    public void authorize(Request req, Response res) {
        try {
            int userID = httpHandler.authorizeMW(req, res);
            if (userID == -1) {
                halt();
            }
            req.attribute("user.id", Integer.toString(userID));
        } catch (HaltException e) {
            ResponseManager.setHeaderJSON(401, res);
            halt(gson.toJson(new UnauthorizedResponse()));
        } catch (Exception e) {
            ResponseManager.setHeaderJSON(500, res);
            halt(gson.toJson(new InternalServerErrorResponse(e)));
        }
    }
}