package fiap.com.br.sprint4j.mapper;

import fiap.com.br.sprint4j.domain.models.Person;
import fiap.com.br.sprint4j.dto.PersonCreateRequest;
import fiap.com.br.sprint4j.dto.PersonDto;
import fiap.com.br.sprint4j.dto.PersonUpdateRequest;

public final class PersonMapper {
private PersonMapper() {}

    public static PersonDto toDto(Person p) {
        return new PersonDto(p.getId(), p.getNome(), p.getTelefone(), p.getEmail(), p.getPerfil(), p.getNascimento());
        }
    public static Person fromCreate(PersonCreateRequest r) {
        return Person.builder()
                .nome(r.nome())
                .telefone(r.telefone())
                .email(r.email())
                .perfil(r.perfil())
                .nascimento(r.nascimento())
                .build();
    }
    public static void applyUpdate(Person p, PersonUpdateRequest r) {
        p.setNome(r.nome());
        p.setTelefone(r.telefone());
        p.setEmail(r.email());
        p.setPerfil(r.perfil());
        p.setNascimento(r.nascimento());
    }
}
