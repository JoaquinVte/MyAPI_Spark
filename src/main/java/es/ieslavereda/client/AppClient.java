package es.ieslavereda.client;

import com.google.gson.Gson;
import es.ieslavereda.server.model.API;
import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.entity.Person;
import es.ieslavereda.server.model.entity.Result;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AppClient {

    static JsonTransformer<Person> jsonTransformer = new JsonTransformer<>();

    public static void main(String[] args) {

        // Get all
        getAll();

        // Get by dni
        getByDNI("123");

        // Add a new person
        Person p1 = new Person("445", "Invent", "Invent Invent", 99);
        addPerson(p1);

        // Update person
        Person p2 = new Person("445", "Invent", "Invent Inventado", 99);
        updatePerson(p2);

        // Delete person
        delete(p2.getDni());

    }

    private static void updatePerson(Person p) {

        HttpClient client = HttpClient.newHttpClient();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonTransformer.render(p)))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 500) {

                Result.Error result = jsonTransformer.getError(response.body().toString());
                System.out.println(result.getError());

            } else if (response.statusCode() == 200) {

                Result.Success<Person> result = jsonTransformer.fromJSonToSuccess(response.body().toString(), Person.class);
                Person person = result.getData();
                System.out.println("Actualizdo el usuario:\n" + person);

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private static void addPerson(Person p) {
        HttpClient client = HttpClient.newHttpClient();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON))
                .POST(HttpRequest.BodyPublishers.ofString(jsonTransformer.render(p)))
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 500) {

                Result.Error result = jsonTransformer.getError(response.body().toString());
                System.out.println("Error al añadir:\n" + result.getError());

            } else if (response.statusCode() == 200) {

                Result.Success<Person> result = jsonTransformer.fromJSonToSuccess(response.body().toString(), Person.class);
                Person person = result.getData();
                System.out.println("Añadida la persona:\n" + person);

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private static void getByDNI(String dni) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON + "?dni=" + dni))
                .GET()
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            Person p = jsonTransformer.getObject(response.body().toString(), Person.class);
            System.out.println("El usurio con dni " + dni + " es :\n" + p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAll() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON_ALL))
                .GET()
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            Person[] personas = gson.fromJson(response.body().toString(), Person[].class);
            //Person[] p = jsonTransformer.getObject(response.body().toString(), Person.class);
            System.out.println("Listado de personas");
            for (Person p : personas)
                System.out.println(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String dni){
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON + "?dni=" + dni))
                .DELETE()
                .build();

        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode()==200) {
                Result.Success<Person> result = jsonTransformer.fromJSonToSuccess(response.body().toString(), Person.class);
                System.out.println("Eliminado:\n" + result.getData());
            } else {
                Result.Error error = jsonTransformer.getError(response.body().toString());
                System.out.println("Error al eliminar: " + error.getError());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
