package com.joaoed.atividade3.services;

import com.joaoed.atividade3.entities.Person;
import com.joaoed.atividade3.entities.PersonDto;
import com.joaoed.atividade3.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    @Transactional(readOnly = true)
    public PersonDto findById(Long id) {
        Person person = repository.findById(id).get();
        return new PersonDto(person);
    }

    @Transactional
    public PersonDto insert(PersonDto dto){
        Person entity = new Person();
        entity.setNome(dto.getNome());
        entity.setCurso(dto.getCurso());
        entity = repository.save(entity);
        return new PersonDto(entity);
    }
    @Transactional
    public PersonDto update(Long id, PersonDto dto) throws Exception {
        try {
            Person entity = repository.getReferenceById(id);
            entity.setNome(dto.getNome());
            entity.setCurso(dto.getCurso());
            entity = repository.save(entity);
            return new PersonDto(entity);
        } catch (EntityNotFoundException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("Id não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new Exception("Violação de integridade");
        }
    }
}
