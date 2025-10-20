package fiap.com.br.sprint4j.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;

import fiap.com.br.sprint4j.Exceptions.BusinessException;
import fiap.com.br.sprint4j.Exceptions.NotFoundException;
import fiap.com.br.sprint4j.domain.enums.Perfil;
import fiap.com.br.sprint4j.domain.models.Person;
import fiap.com.br.sprint4j.dto.PersonCreateRequest;
import fiap.com.br.sprint4j.dto.PersonDto;
import fiap.com.br.sprint4j.dto.PersonUpdateRequest;
import fiap.com.br.sprint4j.repository.PersonRepository;
import fiap.com.br.sprint4j.service.impl.PersonServiceImpl;



@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock PersonRepository repository;
    @InjectMocks PersonServiceImpl service;

    private Person sample() {
        return Person.builder()
                .id(UUID.randomUUID())
                .nome("Fulano")
                .telefone("1199999-0000")
                .email("fulano@mail.com")
                .perfil(Perfil.MEDIO)
                .nascimento(LocalDateTime.of(2000,1,1,0,0))
                .build();
    }

    @Test
    void create_ok() {
        var req = new PersonCreateRequest("Fulano", "1199", "f@mail.com", Perfil.BAIXO, LocalDateTime.now());
        when(repository.existsByEmail("f@mail.com")).thenReturn(false);
        when(repository.save(any(Person.class))).thenAnswer(a -> {
            Person p = a.getArgument(0);
            p.setId(UUID.randomUUID());
            return p;
        });

        PersonDto dto = service.create(req);

        assertThat(dto.id()).isNotNull();
        assertThat(dto.email()).isEqualTo("f@mail.com");
        verify(repository).save(any(Person.class));
    }

    @Test
    void create_emailDuplicado_erro() {
        var req = new PersonCreateRequest("Fulano", "1199", "f@mail.com", Perfil.BAIXO, LocalDateTime.now());
        when(repository.existsByEmail("f@mail.com")).thenReturn(true);

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("E-mail já cadastrado");
        verify(repository, never()).save(any());
    }

    @Test
    void update_ok() {
        var id = UUID.randomUUID();
        var existing = sample();
        existing.setId(id);

        var upd = new PersonUpdateRequest("Novo Nome", "1111", "novo@mail.com", Perfil.ALTO, LocalDateTime.now());

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByEmail("novo@mail.com")).thenReturn(false);
        when(repository.save(any(Person.class))).thenAnswer(a -> a.getArgument(0));

        PersonDto dto = service.update(id, upd);

        assertThat(dto.nome()).isEqualTo("Novo Nome");
        assertThat(dto.email()).isEqualTo("novo@mail.com");
    }

    @Test
    void update_naoEncontrado_erro() {
        var id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        var upd = new PersonUpdateRequest("x","y","z@mail.com", Perfil.MEDIO, LocalDateTime.now());
        assertThatThrownBy(() -> service.update(id, upd))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_emailDuplicado_erro() {
        var id = UUID.randomUUID();
        var existing = sample();
        existing.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByEmail("outro@mail.com")).thenReturn(true);

        var upd = new PersonUpdateRequest(existing.getNome(), existing.getTelefone(), "outro@mail.com",
                existing.getPerfil(), existing.getNascimento());

        assertThatThrownBy(() -> service.update(id, upd))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("E-mail já cadastrado");
    }

    @Test
    void getById_ok() {
        var p = sample();
        when(repository.findById(p.getId())).thenReturn(Optional.of(p));

        var dto = service.getById(p.getId());
        assertThat(dto.nome()).isEqualTo(p.getNome());
    }

    @Test
    void listAll_ok() {
        when(repository.findAll()).thenReturn(List.of(sample(), sample()));
        var list = service.listAll();
        assertThat(list).hasSize(2);
    }

    @Test
    void delete_ok() {
        var id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repository).deleteById(id);
    }

    @Test
    void delete_naoEncontrado_erro() {
        var id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(fiap.com.br.sprint4j.Exceptions.NotFoundException.class);
        verify(repository, never()).deleteById(any());
    }
}
