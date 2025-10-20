package fiap.com.br.sprint4j.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import fiap.com.br.sprint4j.domain.enums.Perfil;

public record PersonDto(
    UUID id,
    String nome,
    String telefone,
    String email,
    Perfil perfil,
    LocalDateTime nascimento
) {

}
