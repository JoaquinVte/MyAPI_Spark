package es.ieslavereda.server.model.service;

import es.ieslavereda.server.model.db.MyDataSource;
import es.ieslavereda.server.model.entity.AuthenticateData;
import es.ieslavereda.server.model.entity.Empleado;
import es.ieslavereda.server.model.entity.Person;
import es.ieslavereda.server.model.entity.Result;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImpEmpleadoService implements IEmpleadoService{

    @Override
    public Result<Empleado> authenticate(AuthenticateData authenticateData) {
        DataSource ds = MyDataSource.getOracleDataSource();
        String sql = "SELECT * FROM EMPLEADO WHERE email=?";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setString(++pos, authenticateData.getEmail());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return new Result.Success<Empleado>(new Empleado("pepa@mordor","Pepa desde servidor"));
            else
                return new Result.Error("Error al autenticar");
        } catch (Exception e){
            return new Result.Error(e.getMessage());
        }
    }
}
