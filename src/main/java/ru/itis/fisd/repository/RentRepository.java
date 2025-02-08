package ru.itis.fisd.repository;

import ru.itis.fisd.database.DBConnection;
import ru.itis.fisd.model.Car;
import ru.itis.fisd.model.Rent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentRepository {

    private final DBConnection db = DBConnection.getInstance();

    /* language=sql */
    private static final String SQL_FIND_ALL = """
            SELECT *
            FROM rents
            WHERE car_id = ?;
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT car_id
            FROM rents
            WHERE user_id = ? and car_id = ?
            """;

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM rents
            WHERE car_id = ? and user_id = ?
            """;
    private static final String SQL_INSERT_NEW_ORDER = """
            INSERT INTO  rents(user_id, car_id, date_start, date_end)
            VALUES (?, ?, ?, ?)
            """;

    public void insert(Rent rent) {
        try (Connection connection = db.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_ORDER);
            preparedStatement.setLong(1, rent.getCustomerId());
            preparedStatement.setLong(2, rent.getCarId());
            preparedStatement.setObject(3, rent.getDateStart());
            preparedStatement.setObject(4, rent.getDateEnd());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Car car, long id) {
        try (Connection connection = db.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
            preparedStatement.setLong(1, car.getId());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long findById(long userId, long carId) {
        try (
                Connection conn = db.getConnection()
        ) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_FIND_BY_ID);

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, carId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Rent> findAll(long id) {
        List<Rent> rents = new ArrayList<>();

        try (
                Connection connection = db.getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Rent order = new Rent(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("car_id"),
                        (Date) resultSet.getObject("date_start"),
                        (Date) resultSet.getObject("date_end")
                );
                rents.add(order);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rents;
    }

}
