package es.ieslavereda.server.model;

public class API {

    public static class Routes {
        public static final String SERVER_BASE = "http://localhost:4567";
        public static final String PERSON = "/person";
        public static final String PERSON_ALL = "/person/all";
    }

    public static class Type {
        public static final String JSON = "application/json";
        public static final String TEXT_XML = "text/xml";
        public static final String TEXT_HTML = "text/html";
    }
}
