package es.ieslavereda.server;

import es.ieslavereda.server.controller.EmpleadoController;
import es.ieslavereda.server.controller.PersonaController;
import es.ieslavereda.server.model.API;
import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.entity.Empleado;
import es.ieslavereda.server.model.entity.Person;
import es.ieslavereda.server.model.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class AppServer {

    static Logger logger = LoggerFactory.getLogger(AppServer.class);

    public static void main(String[] args) {

        //secure("certificados/keystoreServ", "12345678", null, null);
        //port(8080);

//        int maxThreads = 80;
//        int minThreads = 1;
//        int timeOutMillis = 30000;
//        threadPool(maxThreads, minThreads, timeOutMillis);

        before((request, response) -> logger.info("Recibiendo peticion desde " + request.ip()));

        get(API.Routes.PERSON, API.Type.JSON, PersonaController::getPerson, new JsonTransformer<Person>());
        get(API.Routes.PERSON_ALL, API.Type.JSON,(request, response) -> PersonaController.getAllPerson(), new JsonTransformer<Person>());
        //post(API.Routes.PERSON, API.Type.JSON, PersonaController::addPerson,new JsonTransformer<Result<Person>>());
        put(API.Routes.PERSON, API.Type.JSON, PersonaController::updatePerson,new JsonTransformer<Result<Person>>());
        delete(API.Routes.PERSON, API.Type.JSON, PersonaController::delPerson, new JsonTransformer<Result<Person>>());


        // Oracle
        post(API.Routes.ORACLE_AUTH, API.Type.JSON, EmpleadoController::authenticate,new JsonTransformer<Result<Empleado>>());
    }
}
