package fiap.com.br.sprint4j.mapper;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import fiap.com.br.sprint4j.dto.InvestmentCreateRequest;
import fiap.com.br.sprint4j.dto.InvestmentUpdateRequest;

class InvestmentMapperTest {
    
    @Test
    void roundtrip() {
        var now = LocalDateTime.now();
        var create = new InvestmentCreateRequest("CDB", now, 100.0);
        var entity = InvestmentMapper.fromCreate(create);
        assertThat(entity.getNome()).isEqualTo("CDB");

        var dto = InvestmentMapper.toDto(entity);
        assertThat(dto.valor()).isEqualTo(100.0);

        var upd = new InvestmentUpdateRequest("Tesouro", now, 200.0);
        InvestmentMapper.applyUpdate(entity, upd);
        assertThat(entity.getNome()).isEqualTo("Tesouro");
        assertThat(entity.getValor()).isEqualTo(200.0);
    }
}
