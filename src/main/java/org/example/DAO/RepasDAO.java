package org.example.DAO;

import org.example.Entity.Repas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepasDAO extends BaseDAO<Repas> {

    public RepasDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Repas save(Repas element) throws SQLException {
        String request = "INSERT INTO repas (description, date) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, element.getDescription());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(element.getDate()));
            int nbRow = preparedStatement.executeUpdate();
            if (nbRow == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    element.setId_repas(resultSet.getInt(1));
                }
                _connection.commit();
            } else {
                _connection.rollback();
            }
        } catch (SQLException e) {
            _connection.rollback();
            throw e;
        }
        return element;
    }

    @Override
    public Repas update(Repas element) throws SQLException {
        String request = "UPDATE repas SET description = ?, date = ? WHERE id_repas = ?";
        try (PreparedStatement preparedStatement = _connection.prepareStatement(request)) {
            preparedStatement.setString(1, element.getDescription());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(element.getDate()));
            preparedStatement.setInt(3, element.getId_repas());
            preparedStatement.executeUpdate();
            _connection.commit();
        } catch (SQLException e) {
            _connection.rollback();
            throw e;
        }
        return element;
    }

    @Override
    public boolean delete(Repas element) throws SQLException {
        String request = "DELETE FROM repas WHERE id_repas = ?";
        try (PreparedStatement preparedStatement = _connection.prepareStatement(request)) {
            preparedStatement.setInt(1, element.getId_repas());
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                _connection.commit();
                return true;
            } else {
                _connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            _connection.rollback();
            throw e;
        }
    }

    @Override
    public Repas getById(int id) throws SQLException {
        Repas repas = null;
        String request = "SELECT * FROM repas WHERE id_repas = ?";
        try (PreparedStatement preparedStatement = _connection.prepareStatement(request)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                repas = Repas.builder()
                        .id_repas(resultSet.getInt("id_repas"))
                        .description(resultSet.getString("description"))
                        .date(resultSet.getTimestamp("date").toLocalDateTime())
                        .build();
            }
        }
        return repas;
    }

    @Override
    public List<Repas> getAll() throws SQLException {
        List<Repas> repasList = new ArrayList<>();
        String request = "SELECT * FROM repas";
        try (PreparedStatement preparedStatement = _connection.prepareStatement(request);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Repas repas = Repas.builder()
                        .id_repas(resultSet.getInt("id_repas"))
                        .description(resultSet.getString("description"))
                        .date(resultSet.getTimestamp("date").toLocalDateTime())
                        .build();
                repasList.add(repas);
            }
        }
        return repasList;
    }
}

