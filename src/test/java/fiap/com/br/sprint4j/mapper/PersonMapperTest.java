package fiap.com.br.sprint4j.mapper;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import fiap.com.br.sprint4j.domain.enums.Perfil;
import fiap.com.br.sprint4j.dto.PersonCreateRequest;
import fiap.com.br.sprint4j.dto.PersonUpdateRequest;

class PersonMapperTest {
    @Test
    void roundtrip() {
        var create = new PersonCreateRequest("Fulano", "1199", "f@mail.com", Perfil.ALTO, LocalDateTime.now());
        var entity = PersonMapper.fromCreate(create);
        assertThat(entity.getNome()).isEqualTo("Fulano");

        var dto = PersonMapper.toDto(entity);
        assertThat(dto.email()).isEqualTo("f@mail.com");

        var upd = new PersonUpdateRequest("Ciclano", "1100", "c@mail.com", Perfil.BAIXO, dto.nascimento());
        PersonMapper.applyUpdate(entity, upd);
        assertThat(entity.getNome()).isEqualTo("Ciclano");
        assertThat(entity.getEmail()).isEqualTo("c@mail.com");
    }

}
