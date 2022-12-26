package pet.project.spark.repo.todo;

public class Query {
    static final String getUserDataQuery = "SELECT user_id, username, password FROM \"user\" WHERE username = ?";
    static final String registerUser = "INSERT INTO \"user\" (username, password) VALUES (?, ?)";
    static final String createTask = "INSERT INTO task (user_id, task) VALUES (?, ?)";
    static final String getTaskList = "SELECT task_id, task FROM task WHERE user_id = ?";
    static final String updateTask = "UPDATE task SET task = ? WHERE task_id = ?";
}
