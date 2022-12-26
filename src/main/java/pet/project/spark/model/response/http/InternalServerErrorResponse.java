package pet.project.spark.model.response.http;

public class InternalServerErrorResponse {
    private String error;

    public InternalServerErrorResponse(Exception e) {
        this.error = "internal server error. err: " + e;
    }

}
