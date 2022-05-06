package es.ieslavereda.server.controller;

import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.entity.AuthenticateData;
import es.ieslavereda.server.model.entity.Empleado;
import es.ieslavereda.server.model.entity.Result;
import es.ieslavereda.server.model.service.IEmpleadoService;
import es.ieslavereda.server.model.service.ImpEmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class EmpleadoController {

    static Logger logger = LoggerFactory.getLogger(EmpleadoController.class);
    private static final IEmpleadoService service = new ImpEmpleadoService();
    static JsonTransformer<Empleado> jsonTransformer = new JsonTransformer<>();

    public static  Result<Empleado> authenticate(Request req, Response res){
        logger.info("Authenticate: "+ req.body() );
        JsonTransformer<AuthenticateData> jsonTransformer = new JsonTransformer<>();
        AuthenticateData a = (AuthenticateData) jsonTransformer.getObject(req.body(), AuthenticateData.class);
        Result<Empleado> result = service.authenticate(a);
        res.type("application/json");
        res.status((result instanceof Result.Success)?200:500);
        return result;
    }
}
