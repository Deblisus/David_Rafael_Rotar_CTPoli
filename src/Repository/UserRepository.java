package Repository;


import Connection.ConnectionFactory;
import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final Connection connection;

    public UserRepository() {
        this.connection = ConnectionFactory.getConnection();
    }

    public User createUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the values for the prepared statement
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            // Execute the statement and retrieve the generated id
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Get the auto-generated id and set it in the person object
                    int generatedId = rs.getInt("id");
                    user.setId(generatedId);
                    System.out.println("Person created with ID: " + generatedId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public User findUser(String findUsername) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, findUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");

                    User user = new User(username, password);
                    user.setId(id);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
