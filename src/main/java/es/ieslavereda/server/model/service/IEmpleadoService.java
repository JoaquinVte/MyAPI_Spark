package es.ieslavereda.server.model.service;

import es.ieslavereda.server.model.entity.AuthenticateData;
import es.ieslavereda.server.model.entity.Empleado;
import es.ieslavereda.server.model.entity.Result;

public interface IEmpleadoService {
    Result<Empleado> authenticate(AuthenticateData authenticateData);
}
