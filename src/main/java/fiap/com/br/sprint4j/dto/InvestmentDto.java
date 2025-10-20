package fiap.com.br.sprint4j.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record InvestmentDto(
    UUID id,
    String nome,
    LocalDateTime data, 
    double valor
) {

}
