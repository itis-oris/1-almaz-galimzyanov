package ru.itis.fisd.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.fisd.database.DBConnection;
import ru.itis.fisd.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    final static Logger logger = LogManager.getLogger(UserRepository.class);


    private final DBConnection db = DBConnection.getInstance();

    /* language=sql */
    private static final String SQL_UPDATE_BILL = """
            UPDATE users
            SET bill = bill + ?
            WHERE id = ?""";

    private static final String SQL_GET_USER_PARAMETERS = """
            SELECT *
            FROM users
            WHERE name = ?
            """;

    private static final String SQL_UPDATE = """
            UPDATE users
            SET name = ?, password = ?
            WHERE id = ?""";

    private static final String SQL_INSERT_NEW_USER = """
            INSERT INTO users(name, password, role, bill)
            VALUES (?, ?, ?, ?)
            """;



    public User getUser(String name) {
        try (
                Connection connection = db.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER_PARAMETERS);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getLong(("bill"))
                );
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateUser(int id, User user) {
        try (
                Connection connection = db.getConnection();
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();
            db.releaseConnection(connection);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    public boolean updateBill(long id, Long bill) {
        try (
                Connection connection = db.getConnection();
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BILL);

            preparedStatement.setLong(1, bill);
            preparedStatement.setLong(2, id);

            int rowsAffected = preparedStatement.executeUpdate();
            db.releaseConnection(connection);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    public void insert(User user) {
        try (Connection connection = db.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setLong(4, user.getBill());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
