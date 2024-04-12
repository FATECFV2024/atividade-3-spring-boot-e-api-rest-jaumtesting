package com.joaoed.atividade3.controllers;

import com.joaoed.atividade3.entities.PersonDto;
import com.joaoed.atividade3.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/persons")
public class PersonControllers {

    @Autowired
    private PersonService service;
    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Long id){
        PersonDto dto  = service.findById(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<PersonDto> insert(@RequestBody PersonDto dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonDto> update(@PathVariable Long id, @RequestBody PersonDto dto) throws Exception {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
