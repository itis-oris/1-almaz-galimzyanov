package ru.itis.fisd.repository;

import ru.itis.fisd.database.DBConnection;
import ru.itis.fisd.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    private final DBConnection db = DBConnection.getInstance();

    /* language=sql */

    private static final String SQL_COUNT_BY_SELLER = """
            SELECT *
            FROM cars
            WHERE seller_id = ? AND is_available = true;
            """;

    private static final String SQL_COUNT = """
            SELECT *
            FROM cars
            """;

    private static final String SQL_FIND_BY_ID = """
            SELECT *
            FROM cars
            WHERE id = ?
            """;

    private static final String SQL_FIND_ALL = """
            SELECT *
            FROM cars
            WHERE is_for_sale = true AND cars.id not in (
                SELECT rents.car_id FROM rents
                WHERE user_id = ?
            )
            ORDER BY id
            LIMIT ?
            OFFSET ?;
            """;

    private static final String SQL_FIND_BY_SELLER = """
            SELECT *
            FROM cars
            WHERE seller_id = ? AND is_available = true
            LIMIT ?
            OFFSET ?;
            """;

    private static final String SQL_FIND_BY_USER_P = """
            SELECT *
            FROM cars
            JOIN purchases ON cars.id = purchases.car_id
            WHERE purchases.user_id = ?;
            """;

    /* language=sql */
    private static final String SQL_FIND_BY_USER_R = """
            SELECT *
            FROM cars
            JOIN rents ON cars.id = rents.car_id
            WHERE rents.user_id = ?;
            """;


    private static final String SQL_INSERT = """
            INSERT INTO cars(name, seller_id, price, is_available, is_for_sale)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE cars
            SET name = ?, price = ?, is_available = ?, is_for_sale = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM cars
            WHERE id = ?
            """;

    public Car findById(Long id) {
        try (
                Connection conn = db.getConnection()
        ) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_FIND_BY_ID);

            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            Car car = null;
            if (result.next()) {
                car = new Car(
                        result.getLong("id"),
                        result.getLong("seller_id"),
                        result.getString("name"),
                        result.getInt("price"),
                        result.getBoolean("is_available"),
                        result.getBoolean("is_for_sale")
                );
            }
            return car;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Car> findByUser(int limit, int offset, Long id) {
        List<Car> cars = new ArrayList<>();
        List<Car> result = new ArrayList<>();

        try (
                Connection connection = db.getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_P);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            addItems(cars, statement, resultSet);



            statement = connection.prepareStatement(SQL_FIND_BY_USER_R);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            addItems(cars, statement, resultSet);


            for (int i = offset; i < Math.min(offset + limit, cars.size()); i++) {
                result.add(cars.get(i));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void addItems(List<Car> cars, PreparedStatement statement, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Car order = new Car(
                    resultSet.getLong("id"),
                    resultSet.getLong("seller_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getBoolean("is_available"),
                    resultSet.getBoolean("is_for_sale")
            );
            cars.add(order);
        }

        resultSet.close();
        statement.close();
    }

    public List<Car> findAll(int limit, int offset, Long id) {
        List<Car> cars = new ArrayList<>();

        try (
                Connection connection = db.getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);

            statement.setLong(1, id);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            ResultSet resultSet = statement.executeQuery();

            addItems(cars, statement, resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    public List<Car> findBySeller(int limit, int offset, Long id) {
        List<Car> cars = new ArrayList<>();

        try (
                Connection connection = db.getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_SELLER);
            statement.setLong(1, id);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            ResultSet resultSet = statement.executeQuery();

            addItems(cars, statement, resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    public void insert(Car car) {
        try (
                Connection connection = db.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);

            preparedStatement.setString(1, car.getName());
            preparedStatement.setLong(2, car.getSellerId());
            preparedStatement.setInt(3, car.getPrice());
            preparedStatement.setBoolean(4, car.isAvailable());
            preparedStatement.setBoolean(5, car.isForSale());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Car car) {
        try (
                Connection connection = db.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getPrice());
            preparedStatement.setBoolean(3, car.isAvailable());
            preparedStatement.setBoolean(4, car.isForSale());
            preparedStatement.setLong(5, car.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(long id) {
        try (
                Connection connection = db.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int count() {
        try (
                Connection connection = db.getConnection()
        ) {
            int count = 0;
            PreparedStatement statement = connection.prepareStatement(SQL_COUNT);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                count++;
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countBySeller(long id) {
        try (
                Connection connection = db.getConnection()
        ) {
            int count = 0;
            PreparedStatement statement = connection.prepareStatement(SQL_COUNT_BY_SELLER);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                count++;
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countByUser(long id) {
        List<Car> cars = new ArrayList<>();
        try (
                Connection connection = db.getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_P);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            addItems(cars, statement, resultSet);



            statement = connection.prepareStatement(SQL_FIND_BY_USER_R);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            addItems(cars, statement, resultSet);
            return cars.size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
