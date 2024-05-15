package org.example.Entity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Animal {
    private int id_animal;
    private String nom;
    private String habitat;
    private String description;
    private String race;
    private int age;
    private TicketRepas ticketRepas;

    @Override
    public String toString() {
        return "Animal{" +
                "id_animal=" + id_animal +
                ", nom='" + nom + '\'' +
                ", habitat='" + habitat + '\'' +
                ", description='" + description + '\'' +
                ", race='" + race + '\'' +
                ", age=" + age +
                ", ticketRepas=" + ticketRepas +
                '}';
    }
}
