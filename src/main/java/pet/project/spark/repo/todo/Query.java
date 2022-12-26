package pet.project.spark.repo.todo;

public class Query {
    static final String getUserDataQuery = "SELECT user_id, username, password FROM \"user\" WHERE username = ?";
    static final String registerUserQuery = "INSERT INTO \"user\" (username, password) VALUES (?, ?)";
    static final String createTaskQuery = "INSERT INTO task (user_id, task) VALUES (?, ?)";
    static final String getTaskListQuery = "SELECT task_id, task FROM task WHERE user_id = ?";
    static final String updateTaskQuery = "UPDATE task SET task = ? WHERE task_id = ?";
    static final String deleteTaskQuery = "DELETE FROM task WHERE task_id = ?";
}

