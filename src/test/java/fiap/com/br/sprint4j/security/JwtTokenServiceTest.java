package fiap.com.br.sprint4j.security;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import fiap.com.br.sprint4j.domain.enums.UserRole;
import fiap.com.br.sprint4j.domain.models.User;

class JwtTokenServiceTest {
JwtTokenService jwt;

    @BeforeEach
    void setUp() {
        jwt = new JwtTokenService();
        ReflectionTestUtils.setField(jwt, "secret", "01234567890123456789012345678901");
        ReflectionTestUtils.setField(jwt, "issuer", "test-issuer");
        ReflectionTestUtils.setField(jwt, "expirationMinutes", 5L);
    }

    @Test
    void generate_and_validate_token() {
        var user = User.builder()
                .username("bruno")
                .passwordHash("x")
                .roles(Set.of(UserRole.ROLE_USER))
                .enabled(true)
                .build();

        var token = jwt.generateToken(user);
        assertThat(token).isNotBlank();
        assertThat(jwt.isValid(token)).isTrue();
        assertThat(jwt.extractUsername(token)).isEqualTo("bruno");
    }

    @Test
    void invalid_token_returns_false() {
        assertThat(jwt.isValid("abc.def.ghi")).isFalse();
    }
}
