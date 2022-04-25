package es.ieslavereda.config;

import java.io.FileInputStream;
import java.util.Properties;

public class MyConfig {

    private final static MyConfig instancia = new MyConfig();

    private final String defaultFile = "default.properties";
    private final String appFile = "app.properties";
    private final Properties properties;

    private MyConfig() {

        Properties defaultProperties = new Properties();

        try (FileInputStream fis = new FileInputStream(defaultFile)) {

            defaultProperties.load(fis);

        } catch (Exception e) {
            e.printStackTrace();
        }

        properties = new Properties(defaultProperties);

        try (FileInputStream fis = new FileInputStream(appFile)) {

            properties.load(fis);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static MyConfig getInstance(){
        return instancia;
    }
    public String getDBUrl(){
        return properties.getProperty("MYSQL_DB_URL");
    }
    public String getDBUser(){
        return properties.getProperty("MYSQL_DB_USERNAME");
    }
    public String getDBPassword(){
        return properties.getProperty("MYSQL_DB_PASSWORD");
    }
    public String getServerIP(){
        return properties.getProperty("SERVER_IP");
    }
    public String getServerPort(){
        return properties.getProperty("SERVER_PORT");
    }

    public String getBaseURL() {
        return "http://" + getServerIP() +":"+getServerPort();
    }
}