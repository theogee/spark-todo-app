package pet.project.spark.repo.todo;

public class Query {
    static final String getUserDataQuery = "SELECT user_id, username, password FROM \"user\" WHERE username = ?";
    static final String registerUser = "INSERT INTO \"user\" (username, password) VALUES (?, ?)";
}
