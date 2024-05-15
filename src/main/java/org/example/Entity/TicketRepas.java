package org.example.Entity;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TicketRepas {
    private int id_ticketRepas;
    private String nourriture;
    private LocalDateTime dateRepas;
    private Animal animal;


    @Override
    public String toString() {
        return "TicketRepas{" +
                "id_ticketRepas=" + id_ticketRepas +
                ", nourriture='" + nourriture + '\'' +
                ", dateRepas=" + dateRepas +
                ", animal=" + animal +
                '}';
    }
}
