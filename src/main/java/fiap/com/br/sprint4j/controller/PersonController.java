package fiap.com.br.sprint4j.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fiap.com.br.sprint4j.dto.PersonCreateRequest;
import fiap.com.br.sprint4j.dto.PersonDto;
import fiap.com.br.sprint4j.dto.PersonUpdateRequest;
import fiap.com.br.sprint4j.service.PersonService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
@Tag(name = "Persons")
public class PersonController {
private final PersonService service;

    @PostMapping
    public ResponseEntity<PersonDto> create(@Valid @RequestBody PersonCreateRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> update(@PathVariable UUID id, @Valid @RequestBody PersonUpdateRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
