package fiap.com.br.sprint4j.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fiap.com.br.sprint4j.Exceptions.BusinessException;
import fiap.com.br.sprint4j.Exceptions.NotFoundException;
import fiap.com.br.sprint4j.domain.enums.UserRole;
import fiap.com.br.sprint4j.domain.models.User;
import fiap.com.br.sprint4j.dto.UserCreateRequest;
import fiap.com.br.sprint4j.dto.UserDto;
import fiap.com.br.sprint4j.repository.UserRepository;
import fiap.com.br.sprint4j.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public UserDto register(UserCreateRequest request) {
        if (repository.existsByUsername(request.username())) {
            throw new BusinessException("Username já utilizado.");
        }
        User user = User.builder()
                .username(request.username())
                .passwordHash(encoder.encode(request.password()))
                .roles(Set.of(UserRole.ROLE_USER))
                .enabled(true)
                .build();
        user = repository.save(user);
        return new UserDto(user.getId(), user.getUsername(), user.getRoles(), user.isEnabled());
    }

    @Override
    public Optional<User> findByUsernameRaw(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserDto getById(UUID id) {
        User u = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        return new UserDto(u.getId(), u.getUsername(), u.getRoles(), u.isEnabled());
    }
}
