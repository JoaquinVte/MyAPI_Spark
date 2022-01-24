package es.ieslavereda.server;

import com.google.gson.Gson;
import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.API;
import es.ieslavereda.server.model.Persona;
import es.ieslavereda.server.controller.PersonaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;
import static spark.Spark.get;

public class App {
    // View example at https://localhost:4567/secureHello
    static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        //secure("deploy/keystore.p12", "12345678", null, null);
        before((request, response) -> {
            logger.debug("Recibiendo peticion desde " + request.ip());
        });
        get("/secureHello", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Persona persona = new Persona("123","Joaquin","Alonso Saiz",44);
                String json = new Gson().toJson(persona);
                response.type("application/json");
                response.body(json);
                return persona;
            }
        });

        get(API.Routes.Persona,API.Type.JSON,(request, response) -> PersonaController.getPerson(request,response), new JsonTransformer());

        post(API.Routes.Persona,API.Type.JSON,(request, response) -> PersonaController.addPerson(request,response));

    }
}
