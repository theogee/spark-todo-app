package pet.project.spark.util.response;

import spark.Response;

public class ResponseManager {
     public static void setHeaderJSON(int status, Response res) {
        res.status(status);
        res.type("application/json");
    }
}
