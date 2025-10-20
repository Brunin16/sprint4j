package fiap.com.br.sprint4j.dto;

import java.util.Set;
import java.util.UUID;

import fiap.com.br.sprint4j.domain.enums.UserRole;

public record UserDto(
    UUID id, String username, Set<UserRole> roles, boolean enabled
) {

}
