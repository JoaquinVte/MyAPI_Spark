package es.ieslavereda.server.controller;

import es.ieslavereda.server.model.API;
import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PersonaController {
    static Logger logger = LoggerFactory.getLogger(PersonaController.class);

    static Map<String, Person> allPersons = new HashMap<>();
    static {
        allPersons.put("123",new Person("123","Joaquin","Alonso Saiz",44));
        allPersons.put("222",new Person("222","Pedro","Lopez Lopez",45));
    }

    static JsonTransformer<Person> jsonTransformer = new JsonTransformer<>();

    public static Person addPerson(Request req, Response res){
        logger.info("Add person: "+ req.body() );
        Person p = jsonTransformer.getObject(req.body(), Person.class);
        allPersons.put(p.getDni(),p);
        res.type("application/json");
        res.status(200);

        return p;
    }

    public static Person getPerson(Request request, Response response) {
        logger.info("Request person by dni: " + request.queryParams("dni") );
        String dni = request.queryParams("dni");
        response.type(API.Type.JSON);
        response.status(200);
        return allPersons.get(dni);
    }

    public static Map<String, Person> getAllPerson() {
        logger.info("Request all persons");
        return allPersons;
    }
}
