package es.ieslavereda.server;

import es.ieslavereda.server.controller.PersonaController;
import es.ieslavereda.server.model.API;
import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class AppServer {

    static Logger logger = LoggerFactory.getLogger(AppServer.class);

    public static void main(String[] args) {

        //secure("certificados/keystore.p12", "12345678", null, null);
        //port(8080);
        before((request, response) -> logger.info("Recibiendo peticion desde " + request.ip()));

        get(API.Routes.PERSON, API.Type.JSON,(request, response) -> PersonaController.getPerson(request,response), new JsonTransformer<Person>());
        get(API.Routes.PERSON_ALL, API.Type.JSON,(request, response) -> PersonaController.getAllPerson(), new JsonTransformer<Person>());
        post(API.Routes.PERSON, API.Type.JSON,(request, response) -> PersonaController.addPerson(request,response),new JsonTransformer<Person>());

    }
}
