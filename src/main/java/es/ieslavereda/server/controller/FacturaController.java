package es.ieslavereda.server.controller;

import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.entity.Empleado;
import es.ieslavereda.server.model.entity.Factura;
import es.ieslavereda.server.model.entity.Result;
import es.ieslavereda.server.model.service.IEmpleadoService;
import es.ieslavereda.server.model.service.IFacturaService;
import es.ieslavereda.server.model.service.ImpEmpleadoService;
import es.ieslavereda.server.model.service.ImpFacturaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class FacturaController {

    static Logger logger = LoggerFactory.getLogger(FacturaController.class);
    private static final IFacturaService service = new ImpFacturaService();
    static JsonTransformer<Empleado> jsonTransformer = new JsonTransformer<>();


}
