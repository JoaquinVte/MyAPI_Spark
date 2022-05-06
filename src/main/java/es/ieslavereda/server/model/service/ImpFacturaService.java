package es.ieslavereda.server.model.service;

import es.ieslavereda.server.model.db.MyDataSource;
import es.ieslavereda.server.model.entity.Factura;

import java.sql.*;

public class ImpFacturaService implements IFacturaService{
    @Override
    public int createFactura(int clienteId) {

        int numeroFactura = 0;

        String sql = "{call crear_factura(?,?)}";
        try(Connection con = MyDataSource.getMySQLDataSource().getConnection();
            CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2,clienteId);

            cs.execute();

            numeroFactura = cs.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numeroFactura;
    }
}
