package fiap.com.br.sprint4j.service;

import java.util.List;
import java.util.UUID;

import fiap.com.br.sprint4j.dto.InvestmentCreateRequest;
import fiap.com.br.sprint4j.dto.InvestmentDto;
import fiap.com.br.sprint4j.dto.InvestmentUpdateRequest;

public interface InvestmentService {
    InvestmentDto create(InvestmentCreateRequest request);
    InvestmentDto update(UUID id, InvestmentUpdateRequest request);
    void delete(UUID id);
    InvestmentDto getById(UUID id);
    List<InvestmentDto> listAll();
}
