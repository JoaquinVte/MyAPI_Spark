package es.ieslavereda.server.controller;

import es.ieslavereda.server.model.API;
import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PersonaController {
    static Logger logger = LoggerFactory.getLogger(PersonaController.class);

    static Map<String,Persona> misPersonas = new HashMap<>();
    static JsonTransformer<Persona> jsonTransformer = new JsonTransformer<>();

    public static Object addPerson(Request req, Response res){

        Persona p = jsonTransformer.getObject(req.body(),Persona.class);
        logger.info("Add person: "+ p );
        misPersonas.put(p.getDni(),p);
        res.type("application/json");
        res.status(200);

        return jsonTransformer.render(p);
    }

    public static Object getPerson(Request request, Response response) {
        String dni = request.queryParams("dni");
        logger.info("Request person with dni="+dni);
        response.type(API.Type.JSON);
        response.status(200);
        return misPersonas.get(dni);
    }
}
