package fiap.com.br.sprint4j.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Investment {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 20)
    private LocalDateTime data;

    @Column(nullable = false)
    private double valor;
}
