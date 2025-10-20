package fiap.com.br.sprint4j.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fiap.com.br.sprint4j.domain.models.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    boolean existsByEmail(String email);
}
