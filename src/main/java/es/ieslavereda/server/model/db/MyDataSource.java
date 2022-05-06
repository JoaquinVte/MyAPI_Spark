package es.ieslavereda.server.model.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import es.ieslavereda.config.MyConfig;
import oracle.jdbc.datasource.impl.OracleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class MyDataSource {

    public static DataSource getMySQLDataSource() {

        // Propiedades donde tenemos los datos de acceso a la BD

        Properties props = new Properties();

        // Objeto DataSource que devolveremos

        MysqlDataSource mysqlDS = null;

        // Generamos el DataSource con los datos URL, user y passwd necesarios

        mysqlDS = new MysqlDataSource();

        mysqlDS.setURL(MyConfig.getInstance().getDBUrl());
        mysqlDS.setUser(MyConfig.getInstance().getDBUser());
        mysqlDS.setPassword(MyConfig.getInstance().getDBPassword());

        return mysqlDS;

    }

    public static DataSource getOracleDataSource() {


        OracleDataSource oracleDS = null;
        try {
            oracleDS = new OracleDataSource();
            oracleDS.setURL("jdbc:oracle:thin:@10.0.0.21:1521:xe");
            oracleDS.setUser("C##JOAQUIN");
            oracleDS.setPassword("1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return oracleDS;

    }
}