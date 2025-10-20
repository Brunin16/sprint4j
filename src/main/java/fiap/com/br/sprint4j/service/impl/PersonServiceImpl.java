package fiap.com.br.sprint4j.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fiap.com.br.sprint4j.Exceptions.BusinessException;
import fiap.com.br.sprint4j.Exceptions.NotFoundException;
import fiap.com.br.sprint4j.domain.models.Person;
import fiap.com.br.sprint4j.dto.PersonCreateRequest;
import fiap.com.br.sprint4j.dto.PersonDto;
import fiap.com.br.sprint4j.dto.PersonUpdateRequest;
import fiap.com.br.sprint4j.mapper.PersonMapper;
import fiap.com.br.sprint4j.repository.PersonRepository;
import fiap.com.br.sprint4j.service.PersonService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{
    private final PersonRepository repository;

    @Override
    public PersonDto create(PersonCreateRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new BusinessException("E-mail já cadastrado.");
        }
        Person p = PersonMapper.fromCreate(request);
        p = repository.save(p);
        return PersonMapper.toDto(p);
    }

    @Override
    public PersonDto update(UUID id, PersonUpdateRequest request) {
        Person p = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada."));
        // se alterou e-mail, validar unicidade
        if (!p.getEmail().equalsIgnoreCase(request.email()) && repository.existsByEmail(request.email())) {
            throw new BusinessException("E-mail já cadastrado.");
        }
        PersonMapper.applyUpdate(p, request);
        p = repository.save(p);
        return PersonMapper.toDto(p);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) throw new NotFoundException("Pessoa não encontrada.");
        repository.deleteById(id);
    }

    @Override
    public PersonDto getById(UUID id) {
        return repository.findById(id)
                .map(PersonMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada."));
    }

    @Override
    public List<PersonDto> listAll() {
        return repository.findAll().stream().map(PersonMapper::toDto).toList();
    }
}
