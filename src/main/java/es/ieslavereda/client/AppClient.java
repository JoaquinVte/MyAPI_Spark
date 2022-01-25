package es.ieslavereda.client;

import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.Person;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AppClient {

    static JsonTransformer<Person> jsonTransformer = new JsonTransformer<>();

    public static void main(String[] args) {


        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/secure"))
                .GET()
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Person p = jsonTransformer.getObject(response.body().toString(), Person.class);
            System.out.println(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
