package fiap.com.br.sprint4j.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import fiap.com.br.sprint4j.Exceptions.BusinessException;
import fiap.com.br.sprint4j.domain.enums.UserRole;
import fiap.com.br.sprint4j.domain.models.User;
import fiap.com.br.sprint4j.dto.UserCreateRequest;
import fiap.com.br.sprint4j.dto.UserDto;
import fiap.com.br.sprint4j.repository.UserRepository;
import fiap.com.br.sprint4j.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock UserRepository repository;
    @Mock PasswordEncoder encoder;
    @InjectMocks UserServiceImpl service;

    @Test
    void register_ok() {
        var req = new UserCreateRequest("bruno", "123456");
        when(repository.existsByUsername("bruno")).thenReturn(false);
        when(encoder.encode("123456")).thenReturn("hash");
        var saved = User.builder()
                .id(UUID.randomUUID())
                .username("bruno")
                .passwordHash("hash")
                .roles(Set.of(UserRole.ROLE_USER))
                .enabled(true)
                .build();
        when(repository.save(any(User.class))).thenReturn(saved);

        UserDto dto = service.register(req);

        assertThat(dto.username()).isEqualTo("bruno");
        assertThat(dto.roles()).contains(UserRole.ROLE_USER);
        verify(repository).existsByUsername("bruno");
        verify(repository).save(any(User.class));
    }

    @Test
    void register_usernameDuplicado_erro() {
        var req = new UserCreateRequest("bruno", "123456");
        when(repository.existsByUsername("bruno")).thenReturn(true);

        assertThatThrownBy(() -> service.register(req))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Username já utilizado");
        verify(repository, never()).save(any());
    }

}
