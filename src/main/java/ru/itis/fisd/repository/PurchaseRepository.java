package ru.itis.fisd.repository;

import ru.itis.fisd.database.DBConnection;
import ru.itis.fisd.model.Purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseRepository {

    private final DBConnection db = DBConnection.getInstance();

    /* language=sql */
    private static final String SQL_FIND_BY_ID = """
            SELECT car_id
            FROM purchases
            WHERE user_id = ? and car_id = ?
            """;

    private static final String SQL_INSERT_NEW_ORDER = """
            INSERT INTO purchases(user_id, car_id, payment)
            VALUES (?, ?, ?)
            """;

    public void insert(Purchase purchase) {
        try (Connection connection = db.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_ORDER);
            preparedStatement.setLong(1, purchase.getCustomerId());
            preparedStatement.setLong(2, purchase.getCarId());
            preparedStatement.setString(3, purchase.getPaymentMethod());
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
}
