package fiap.com.br.sprint4j.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
    @NotBlank @Size(min = 3, max = 100) String username,
    @NotBlank @Size(min = 6, max = 72) String password
) {

}
