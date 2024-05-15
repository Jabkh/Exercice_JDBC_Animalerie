package org.example.Util;

import org.example.DAO.AnimalDAO;
import org.example.DAO.TicketRepasDAO;
import org.example.Entity.Animal;
import org.example.Entity.TicketRepas;



import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Ihm {
    private Connection connection;

    private AnimalDAO animalDAO;

    private TicketRepasDAO ticketRepasDAO;

    private Scanner scanner;

    public Ihm() {
        scanner = new Scanner(System.in);
        try {
            connection = DataBaseManager.getConnection();
            animalDAO = new AnimalDAO(connection);
            ticketRepasDAO = new TicketRepasDAO(connection, animalDAO);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création de l'ihm", e);
        }
    }

    public void start() {
        int choix;
        while (true) {
            System.out.println("1. Ajouter un animal");
            System.out.println("2. Lister les animaux");
            System.out.println("3. Supprimer un animal");
            System.out.println("4. Rechercher un animal");
            System.out.println("5. Ajouter un repas");
            choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix) {
                case 1:
                    ajouterAnimal();
                    break;
                case 2:
                    listerAnimaux();
                    break;
                case 3:
                    supprimerAnimal();
                    break;
                case 4:
                    rechercherAnimaux();
                    break;
                case 5:
                    ajouterRepas();
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }

    private void ajouterRepas() {
        System.out.println("Création d'un nouveau ticket repas");
        System.out.println("Nom du ticket repas : ");
        String nourriture = scanner.nextLine();
        System.out.println("Date du ticket repas (format yyyy-MM-dd HH:mm:ss) : ");
        String dateRepasStr = scanner.nextLine();
        LocalDateTime dateRepas = LocalDateTime.parse(dateRepasStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("Id de l'animal : ");
        int animal_id = scanner.nextInt();
        scanner.nextLine();

        try {
            TicketRepas ticketRepas = ticketRepasDAO.save(TicketRepas.builder()
                    .nourriture(nourriture)
                    .dateRepas(dateRepas)
                    .animal(animalDAO.getById(animal_id))
                    .build());

            System.out.println("Ticket repas ajouté avec succès : " + ticketRepas);
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du ticket repas : " + e.getMessage());
        }
    }



    private void ajouterAnimal() {
        System.out.println("Création d'un nouvel animal");
        System.out.println("Nom de l'animal : ");
        String nom = scanner.nextLine();
        System.out.println("Race de l'animal : ");
        String race = scanner.nextLine();
        System.out.println("Habitat de l'animal : ");
        String habitat = scanner.nextLine();
        System.out.println("Description de l'animal : ");
        String description = scanner.nextLine();
        System.out.println("Age de l'animal : ");
        int age = scanner.nextInt();

        try {
            Animal animal = animalDAO.save(Animal.builder()
                    .nom(nom)
                    .race(race)
                    .description(description)
                    .habitat(habitat)
                    .age(age)
                    .build());

            System.out.println("Animal ajouté avec succès : " + animal);
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'animal : " + e.getMessage());
        }
    }


    private void listerAnimaux() {
        try {
            List<Animal> animaux = animalDAO.getAll();
            for (Animal animal : animaux) {
                System.out.println(animal);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des animaux");
        }
    }

    private void supprimerAnimal() {
        System.out.println("Entrez l'id de l'animal à supprimer : ");
        int id = scanner.nextInt();
        try {
            animalDAO.supprimerAnimal(id);
            System.out.println("Animal supprimé avec l'id "  + id);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'animal");
        }
    }

    private void rechercherAnimaux() {
        System.out.println("Recherche d'animaux :");
        System.out.println("1. Par nom");
        System.out.println("2. Par habitat");
        System.out.println("3. Par race");
        System.out.println("4. Par âge");
        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1:
                rechercherParNom();
                break;
            case 2:
                rechercherParHabitat();
                break;
            case 3:
                rechercherParRace();
                break;
            case 4:
                rechercherParAge();
                break;
            default:
                System.out.println("Choix invalide");
        }
    }

    private void rechercherParNom() {
        System.out.println("Entrez le nom de l'animal à rechercher : ");
        String nom = scanner.nextLine();
        try {
            Animal animal = animalDAO.getByName(nom);
            if (animal != null) {
                System.out.println("Animal trouvé :");
                System.out.println(animal);
            } else {
                System.out.println("Aucun animal trouvé pour le nom " + nom);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche d'animaux par nom : " + e.getMessage());
        }
    }

    private void rechercherParHabitat() {
        System.out.println("Entrez l'habitat de l'animal à rechercher : ");
        String habitat = scanner.nextLine();
        try {
            Animal animal = animalDAO.getByHabitation(habitat);
            if (animal != null) {
                System.out.println("Animal trouvé :");
                System.out.println(animal);
            } else {
                System.out.println("Aucun animal trouvé pour le habitat " + habitat);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche d'animaux par habitat : " + e.getMessage());
        }
    }

    private void rechercherParRace() {
        System.out.println("Entrez la race de l'animal à rechercher : ");
        String race = scanner.nextLine();
        try {
            Animal animal = animalDAO.getByRace(race);
            if (animal != null) {
                System.out.println("Animal trouvé :");
                System.out.println(animal);
            } else {
                System.out.println("Aucun animal trouvé pour le race " + race);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche d'animaux par race : " + e.getMessage());
        }
    }

    private void rechercherParAge() {
        System.out.println("Entrez l'âge de l'animal à rechercher : ");
        int age = scanner.nextInt();
        try {
            Animal animal = animalDAO.getByAge(age);
            if (animal != null) {
                System.out.println("Animal trouvé :");
                System.out.println(animal);
            } else {
                System.out.println("Aucun animal trouvé pour l'âge " + age);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche d'animaux par âge : " + e.getMessage());
        }
    }
}

