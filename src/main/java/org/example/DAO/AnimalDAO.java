package org.example.DAO;

import org.example.Entity.Animal;
import org.example.Entity.TicketRepas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO extends BaseDAO<Animal> {
    public AnimalDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Animal save(Animal element) throws SQLException {
        try {
            request = "INSERT INTO animal (nom,habitat,race,description,age) VALUES (?,?,?,?,?)";
            preparedStatement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, element.getNom());
            preparedStatement.setString(2, element.getHabitat());
            preparedStatement.setString(3, element.getRace());
            preparedStatement.setString(4, element.getDescription());
            preparedStatement.setInt(5, element.getAge());
            int nbrow = preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            Animal animal = null;
            if (resultSet.next()) {
                animal = Animal.builder()
                        .id_animal(resultSet.getInt(1))
                        .nom(element.getNom())
                        .habitat(element.getHabitat())
                        .race(element.getRace())
                        .description(element.getDescription())
                        .age(element.getAge())
                        .build();
            }
            if (nbrow != 1) {
                System.out.println("erreur à l'ajout if nbrow");
                _connection.rollback();
            }
            _connection.commit();
            return animal;
        } catch (SQLException e) {
            System.out.println("erreur à l'ajout SQLException" + e.getMessage());
            _connection.rollback();
            return null;
        }
    }

    @Override
    public Animal update(Animal element) {
        return null;
    }

    @Override
    public boolean delete(Animal element) {
        return false;
    }

    @Override
    public Animal getById(int id) throws SQLException {
        request = "SELECT * FROM animal WHERE id_animal = ?";
        preparedStatement = _connection.prepareStatement(request);
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Animal.builder()
                    .id_animal(resultSet.getInt("id_animal"))
                    .nom(resultSet.getString("nom"))
                    .habitat(resultSet.getString("habitat"))
                    .race(resultSet.getString("race"))
                    .description(resultSet.getString("description"))
                    .age(resultSet.getInt("age"))
                    .build();
        }
        return null;
    }

    public Animal getByRace(String race) throws SQLException {
        request = "SELECT * FROM animal WHERE race = ?";
        preparedStatement = _connection.prepareStatement(request);
        preparedStatement.setString(1, race);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Animal.builder()
                    .id_animal(resultSet.getInt("id_animal"))
                    .nom(resultSet.getString("nom"))
                    .habitat(resultSet.getString("habitat"))
                    .race(resultSet.getString("race"))
                    .description(resultSet.getString("description"))
                    .age(resultSet.getInt("age"))
                    .build();
        }
        return null;
    }

    public Animal getByHabitation(String habitat) throws SQLException {
        request = "SELECT * FROM animal WHERE habitat = ?";
        preparedStatement = _connection.prepareStatement(request);
        preparedStatement.setString(1, habitat);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Animal.builder()
                    .id_animal(resultSet.getInt("id_animal"))
                    .nom(resultSet.getString("nom"))
                    .habitat(resultSet.getString("habitat"))
                    .race(resultSet.getString("race"))
                    .description(resultSet.getString("description"))
                    .age(resultSet.getInt("age"))
                    .build();
        }
        return null;
    }

    public Animal getByName(String nom) throws SQLException {
        request = "SELECT * FROM animal WHERE nom = ?";
        preparedStatement = _connection.prepareStatement(request);
        preparedStatement.setString(1, nom);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Animal.builder()
                    .id_animal(resultSet.getInt("id_animal"))
                    .nom(resultSet.getString("nom"))
                    .habitat(resultSet.getString("habitat"))
                    .race(resultSet.getString("race"))
                    .description(resultSet.getString("description"))
                    .age(resultSet.getInt("age"))
                    .build();
        }
        return null;
    }


    public Animal getByAge(int age) throws SQLException {
        request = "SELECT * FROM animal WHERE age = ?";
        preparedStatement = _connection.prepareStatement(request);
        preparedStatement.setInt(1, age);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Animal.builder()
                    .id_animal(resultSet.getInt("id_animal"))
                    .nom(resultSet.getString("nom"))
                    .habitat(resultSet.getString("habitat"))
                    .race(resultSet.getString("race"))
                    .description(resultSet.getString("description"))
                    .age(resultSet.getInt("age"))
                    .build();
        }
        return null;
    }

    @Override
    public List<Animal> getAll() throws SQLException {
        List<Animal> animaux = new ArrayList<>();
        request = "SELECT * FROM animal";
        preparedStatement = _connection.prepareStatement(request);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            animaux.add(Animal.builder()
                    .id_animal(resultSet.getInt("id_animal"))
                    .nom(resultSet.getString("nom"))
                    .habitat(resultSet.getString("habitat"))
                    .race(resultSet.getString("race"))
                    .description(resultSet.getString("description"))
                    .age(resultSet.getInt("age"))
                    .build());
        }
        return animaux;
    }

    public void supprimerAnimal(int id) throws SQLException {
        request = "DELETE FROM animal WHERE id_animal = ?";
        try {
            preparedStatement = _connection.prepareStatement(request);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            _connection.commit();
        } catch (SQLException e) {
            _connection.rollback();
        }
    }
}
