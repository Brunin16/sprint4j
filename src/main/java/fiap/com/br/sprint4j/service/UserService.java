package fiap.com.br.sprint4j.service;

import java.util.Optional;
import java.util.UUID;

import fiap.com.br.sprint4j.domain.models.User;
import fiap.com.br.sprint4j.dto.UserCreateRequest;
import fiap.com.br.sprint4j.dto.UserDto;

public interface UserService {
    UserDto register(UserCreateRequest request);
    Optional<User> findByUsernameRaw(String username);
    UserDto getById(UUID id);
}
