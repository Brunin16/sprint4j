package fiap.com.br.sprint4j.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.junit.jupiter.MockitoExtension;

import fiap.com.br.sprint4j.Exceptions.NotFoundException;
import fiap.com.br.sprint4j.domain.models.Investment;
import fiap.com.br.sprint4j.dto.InvestmentCreateRequest;
import fiap.com.br.sprint4j.dto.InvestmentUpdateRequest;
import fiap.com.br.sprint4j.repository.InvestmentRepository;
import fiap.com.br.sprint4j.service.impl.InvestmentServiceImpl;

@ExtendWith(MockitoExtension.class)
class InvestmentServiceImplTest {
    @Mock InvestmentRepository repository;
    @InjectMocks InvestmentServiceImpl service;

    private Investment sample() {
        return Investment.builder()
                .id(UUID.randomUUID())
                .nome("CDB Banco X")
                .data(LocalDateTime.of(2025,1,1,10,0))
                .valor(1000.0)
                .build();
    }

    @Test
    void create_ok() {
        var req = new InvestmentCreateRequest("CDB", LocalDateTime.now(), 500.0);
        when(repository.save(any(Investment.class))).thenAnswer(a -> {
            Investment i = a.getArgument(0);
            i.setId(UUID.randomUUID());
            return i;
        });

        var dto = service.create(req);
        assertThat(dto.id()).isNotNull();
        assertThat(dto.nome()).isEqualTo("CDB");
    }

    @Test
    void update_ok() {
        var id = UUID.randomUUID();
        var existing = sample();
        existing.setId(id);

        var upd = new InvestmentUpdateRequest("Tesouro IPCA", existing.getData(), 2000.0);
        // idem observação de tipo do campo data.

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(Investment.class))).thenAnswer(a -> a.getArgument(0));

        var dto = service.update(id, upd);
        assertThat(dto.nome()).isEqualTo("Tesouro IPCA");
        assertThat(dto.valor()).isEqualTo(2000.0);
    }

    @Test
    void get_naoEncontrado_erro() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(UUID.randomUUID()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void listAll_ok() {
        when(repository.findAll()).thenReturn(List.of(sample()));
        var list = service.listAll();
        assertThat(list).hasSize(1);
    }

    @Test
    void delete_ok() {
        var id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        service.delete(id);
        verify(repository).deleteById(id);
    }
}
