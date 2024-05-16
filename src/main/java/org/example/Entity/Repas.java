package org.example.Entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Repas {

    private int id_reaps;
    private String nomRepas;
    private Animal animal;
    private Repas repas;

    @Override
    public String toString() {
        return "Repas{" +
                "id_reaps=" + id_reaps +
                ", nomRepas='" + nomRepas + '\'' +
                ", animal=" + animal +
                ", repas=" + repas +
                '}';
    }
}
