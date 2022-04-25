package es.ieslavereda.server.model;

import es.ieslavereda.config.MyConfig;

public class API {

    public static class Routes {
        public static final String SERVER_BASE = "http://"+ MyConfig.getInstance().getServerIP()+":"+MyConfig.getInstance().getServerPort();
        public static final String PERSON = "/person";
        public static final String PERSON_ALL = "/person/all";
    }

    public static class Type {
        public static final String JSON = "application/json";
        public static final String TEXT_XML = "text/xml";
        public static final String TEXT_HTML = "text/html";
    }
}
