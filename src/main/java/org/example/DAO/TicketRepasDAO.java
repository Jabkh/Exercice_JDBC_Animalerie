package org.example.DAO;

import org.example.Entity.Animal;
import org.example.Entity.Repas;
import org.example.Entity.TicketRepas;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketRepasDAO extends BaseDAO<TicketRepas> {
    private AnimalDAO animauxDAO;
    private RepasDAO repasDAO;

    public TicketRepasDAO(Connection connection, AnimalDAO animalDAO, RepasDAO repasDAO) {
        super(connection);
        this.animauxDAO = animalDAO;
        this.repasDAO = repasDAO;
    }

    @Override
    public TicketRepas save(TicketRepas element) throws SQLException {
        try {
            String request = "INSERT INTO ticketrepas (nourriture, dateRepas, animal_id, repas_id) VALUES (?, ?, ?, ?)";
            preparedStatement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, element.getNourriture());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(element.getDateRepas()));
            preparedStatement.setInt(3, element.getAnimal().getId_animal());
            preparedStatement.setInt(4, element.getRepas().getId_repas());
            int nbrow = preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            TicketRepas ticketRepas = null;
            if (resultSet.next()) {
                ticketRepas = TicketRepas.builder()
                        .id_ticketRepas(resultSet.getInt(1))
                        .nourriture(element.getNourriture())
                        .dateRepas(element.getDateRepas())
                        .animal(animauxDAO.getById(element.getAnimal().getId_animal()))
                        .repas(repasDAO.getById(element.getRepas().getId_repas()))
                        .build();
            }
            if (nbrow != 1) {
                _connection.rollback();
            }
            _connection.commit();
            return ticketRepas;
        } catch (SQLException e) {
            _connection.rollback();
            return null;
        }
    }

    @Override
    public TicketRepas update(TicketRepas element) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(TicketRepas element) throws SQLException {
        return false;
    }

    @Override
    public TicketRepas getById(int id) throws SQLException {
        String request = "SELECT * FROM ticketRepas WHERE id_ticketRepas = ?";
        preparedStatement = _connection.prepareStatement(request);
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        TicketRepas ticketRepas = null;
        if (resultSet.next()) {
            ticketRepas = TicketRepas.builder()
                    .id_ticketRepas(resultSet.getInt("id_ticketRepas"))
                    .nourriture(resultSet.getString("nourriture"))
                    .dateRepas(resultSet.getTimestamp("dateRepas").toLocalDateTime())
                    .animal(animauxDAO.getById(resultSet.getInt("animal_id")))
                    .repas(repasDAO.getById(resultSet.getInt("repas_id")))
                    .build();
        }
        return ticketRepas;
    }

    public List<TicketRepas> getRepasByAnimalId(int id) throws SQLException {
        String request = "SELECT * FROM ticketRepas WHERE animal_id = ?";
        preparedStatement = _connection.prepareStatement(request);
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        List<TicketRepas> ticketRepasList = new ArrayList<>();
        while (resultSet.next()) {
            TicketRepas ticketRepas = TicketRepas.builder()
                    .id_ticketRepas(resultSet.getInt("id_ticketRepas"))
                    .nourriture(resultSet.getString("nourriture"))
                    .dateRepas(resultSet.getTimestamp("dateRepas").toLocalDateTime())
                    .animal(animauxDAO.getById(resultSet.getInt("animal_id")))
                    .repas(repasDAO.getById(resultSet.getInt("repas_id")))
                    .build();
            ticketRepasList.add(ticketRepas);
        }
        return ticketRepasList;
    }

    @Override
    public List<TicketRepas> getAll() throws SQLException {
        List<TicketRepas> ticketsRepas = new ArrayList<>();
        String request = "SELECT * FROM ticketrepas";
        try (PreparedStatement preparedStatement = _connection.prepareStatement(request);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id_ticketRepas = resultSet.getInt("id_ticketRepas");
                String nourriture = resultSet.getString("nourriture");
                LocalDateTime dateRepas = resultSet.getTimestamp("dateRepas").toLocalDateTime();
                int animal_id = resultSet.getInt("animal_id");
                int repas_id = resultSet.getInt("repas_id");

                Animal animal = animauxDAO.getById(animal_id);
                Repas repas = repasDAO.getById(repas_id);

                TicketRepas ticketRepas = TicketRepas.builder()
                        .id_ticketRepas(id_ticketRepas)
                        .nourriture(nourriture)
                        .dateRepas(dateRepas)
                        .animal(animal)
                        .repas(repas)
                        .build();
                ticketsRepas.add(ticketRepas);
            }
        }
        return ticketsRepas;
    }
}
