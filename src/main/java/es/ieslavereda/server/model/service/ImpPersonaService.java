package es.ieslavereda.server.model.service;

import es.ieslavereda.server.model.db.MyDataSource;
import es.ieslavereda.server.model.entity.Person;
import es.ieslavereda.server.model.entity.Result;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImpPersonaService implements IPersonaService {
    @Override
    public List<Person> findAll() {

        List<Person> people = new ArrayList<>();

        DataSource ds = MyDataSource.getMySQLDataSource();
        String sql = "SELECT * FROM person";
        try (Connection con = ds.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Person person;

            while (rs.next()) {
                person = new Person(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"), rs.getInt("edad"));
                people.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public Result<Person> save(Person person) {
        DataSource ds = MyDataSource.getMySQLDataSource();
        String sql = "insert into person values (?,?,?,?)";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setString(++pos, person.getDni());
            pstmt.setString(++pos, person.getNombre());
            pstmt.setString(++pos, person.getApellidos());
            pstmt.setInt(++pos, person.getEdad());

            pstmt.execute();
            return new Result.Success<Person>(person);
        } catch (Exception e){
            return new Result.Error(e.getMessage());
        }
    }

    @Override
    public Person findById(String dni) {
        Person person = null;
        DataSource ds = MyDataSource.getMySQLDataSource();
        String sql = "SELECT * FROM person WHERE dni LIKE '" + dni + "'";
        try (Connection con = ds.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                person = new Person(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"), rs.getInt("edad"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public Result<Person> update(Person person) {
        DataSource ds = MyDataSource.getMySQLDataSource();
        String sql = "UPDATE person SET nombre=?,apellidos=?,edad=? WHERE dni LIKE ?";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setString(++pos, person.getNombre());
            pstmt.setString(++pos, person.getApellidos());
            pstmt.setInt(++pos, person.getEdad());
            pstmt.setString(++pos, person.getDni());
            int cant = pstmt.executeUpdate();
            if (cant == 1)
                return new Result.Success<Person>(person);
            else
                return new Result.Error("Ninguna persona actualizada");
        } catch (Exception e) {
            return new Result.Error(e.getMessage());
        }

    }

    @Override
    public Result<Person> delete(String dni) {
        DataSource ds = MyDataSource.getMySQLDataSource();
        String sql = "DELETE FROM person WHERE dni LIKE ? RETURNING nombre,apellidos,edad";
        try (Connection con = ds.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            int pos = 0;
            pstmt.setString(++pos, dni);
            int cant = pstmt.executeUpdate();

            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                Person person = new Person(dni, rs.getString("nombre"), rs.getString("apellidos"), rs.getInt("edad"));
                return new Result.Success<Person>(person);
            }
            return new Result.Error("Ninguna persona eliminada");
        } catch (Exception e) {
            return new Result.Error(e.getMessage());
        }
    }
}
