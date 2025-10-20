package fiap.com.br.sprint4j.dto;

import java.time.LocalDateTime;

import fiap.com.br.sprint4j.domain.enums.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonUpdateRequest(
    @NotBlank @Size(max = 150) String nome,
    @Size(max = 20) String telefone,
    @Email @NotBlank @Size(max = 150) String email,
    @NotNull Perfil perfil,
    @NotNull LocalDateTime nascimento
) {

}
