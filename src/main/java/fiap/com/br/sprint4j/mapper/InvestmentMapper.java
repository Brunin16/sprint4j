package fiap.com.br.sprint4j.mapper;

import fiap.com.br.sprint4j.domain.models.Investment;
import fiap.com.br.sprint4j.dto.InvestmentCreateRequest;
import fiap.com.br.sprint4j.dto.InvestmentDto;
import fiap.com.br.sprint4j.dto.InvestmentUpdateRequest;

public final class InvestmentMapper {
    private InvestmentMapper() {}

    public static InvestmentDto toDto(Investment i) {
        return new InvestmentDto(i.getId(), i.getNome(), i.getData(), i.getValor());
    }
    public static Investment fromCreate(InvestmentCreateRequest r) {
        return Investment.builder()
                .nome(r.nome())
                .data(r.data())
                .valor(r.valor())
                .build();
    }
    public static void applyUpdate(Investment i, InvestmentUpdateRequest r) {
        i.setNome(r.nome());
        i.setData(r.data());
        i.setValor(r.valor());
    }
}
