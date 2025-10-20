package fiap.com.br.sprint4j.service;

import java.util.List;
import java.util.UUID;

import fiap.com.br.sprint4j.dto.PersonCreateRequest;
import fiap.com.br.sprint4j.dto.PersonDto;
import fiap.com.br.sprint4j.dto.PersonUpdateRequest;

public interface PersonService {
    PersonDto create(PersonCreateRequest request);
    PersonDto update(UUID id, PersonUpdateRequest request);
    void delete(UUID id);
    PersonDto getById(UUID id);
    List<PersonDto> listAll();
}
