package es.ieslavereda.server.controller;

import es.ieslavereda.server.model.API;
import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.entity.Person;
import es.ieslavereda.server.model.entity.Result;
import es.ieslavereda.server.model.service.IPersonaService;
import es.ieslavereda.server.model.service.ImpPersonaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.List;

public class PersonaController {
    static Logger logger = LoggerFactory.getLogger(PersonaController.class);

    private static final IPersonaService service = new ImpPersonaService();

    static JsonTransformer<Person> jsonTransformer = new JsonTransformer<>();


    public static Result<Person> addPerson(Request req, Response res){
        logger.info("Add person: "+ req.body() );
        Person p = jsonTransformer.getObject(req.body(), Person.class);
        Result<Person> result = service.save(p);
        res.type("application/json");
        res.status((result instanceof Result.Success)?200:500);
        return result;
    }

    public static Result<Person> updatePerson(Request request, Response res) {
        logger.info("Updating person ");
        Person p = jsonTransformer.getObject(request.body(), Person.class);
        Result<Person> result = service.update(p);
        res.type("application/json");
        res.status((result instanceof Result.Success)?200:500);
        return result;
    }

    public static Person getPerson(Request request, Response response) {
        logger.info("Request person by dni: " + request.queryParams("dni") );
        String dni = request.queryParams("dni");
        response.type(API.Type.JSON);
        response.status(200);
        response.body();
        return service.findById(dni);
    }

    public static List<Person> getAllPerson() {
        logger.info("Request all persons");
        return service.findAll();
    }

    public static Result<Person> delPerson(Request request, Response res) {
        logger.info("Request person by dni: " + request.queryParams("dni") );
        String dni = request.queryParams("dni");
        Result<Person> result = service.delete(dni);
        res.type("application/json");
        res.status((result instanceof Result.Success)?200:500);
        return result;
    }
}
