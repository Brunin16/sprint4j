package fiap.com.br.sprint4j.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fiap.com.br.sprint4j.Exceptions.NotFoundException;
import fiap.com.br.sprint4j.domain.models.Investment;
import fiap.com.br.sprint4j.dto.InvestmentCreateRequest;
import fiap.com.br.sprint4j.dto.InvestmentDto;
import fiap.com.br.sprint4j.dto.InvestmentUpdateRequest;
import fiap.com.br.sprint4j.mapper.InvestmentMapper;
import fiap.com.br.sprint4j.repository.InvestmentRepository;
import fiap.com.br.sprint4j.service.InvestmentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService{
     private final InvestmentRepository repository;

    @Override
    public InvestmentDto create(InvestmentCreateRequest request) {
        Investment i = InvestmentMapper.fromCreate(request);
        i = repository.save(i);
        return InvestmentMapper.toDto(i);
    }

    @Override
    public InvestmentDto update(UUID id, InvestmentUpdateRequest request) {
        Investment i = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Investment não encontrado."));
        InvestmentMapper.applyUpdate(i, request);
        i = repository.save(i);
        return InvestmentMapper.toDto(i);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) throw new NotFoundException("Investment não encontrado.");
        repository.deleteById(id);
    }

    @Override
    public InvestmentDto getById(UUID id) {
        return repository.findById(id)
                .map(InvestmentMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Investment não encontrado."));
    }

    @Override
    public List<InvestmentDto> listAll() {
        return repository.findAll().stream().map(InvestmentMapper::toDto).toList();
    }
}
