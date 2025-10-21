package fiap.com.br.sprint4j.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record InvestmentCreateRequest(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank LocalDateTime data,
        @Positive double valor
) {

}
