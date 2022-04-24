package es.ieslavereda.server.model.service;

import es.ieslavereda.server.model.entity.Person;
import es.ieslavereda.server.model.entity.Result;

import java.sql.SQLException;
import java.util.List;

public interface IPersonaService {

    List<Person> findAll();
    Result<Person> save(Person person);
    Person findById(String dni);
    Result<Person> update(Person person);
    Result<Person> delete(String dni);

}
