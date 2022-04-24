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
        getByDNI();

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
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 500) {

                Result.Error result = jsonTransformer.getError(response.body());
                System.out.println(result.getError());

            } else if (response.statusCode() == 200) {

                Result.Success<Person> result = jsonTransformer.fromJSonToSuccess(response.body(), Person.class);
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
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 500) {

                Result.Error result = jsonTransformer.getError(response.body());
                System.out.println("Error al añadir:\n" + result.getError());

            } else if (response.statusCode() == 200) {

                Result.Success<Person> result = jsonTransformer.fromJSonToSuccess(response.body(), Person.class);
                Person person = result.getData();
                System.out.println("Añadida la persona:\n" + person);

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private static void getByDNI() {

        String dni = "123";
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON + "?dni=" + dni))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Person p = jsonTransformer.getObject(response.body(), Person.class);
            System.out.println("El usurio con dni " + "123" + " es :\n" + p);
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
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            Person[] personas = gson.fromJson(response.body(), Person[].class);
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
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode()==200) {
                Result.Success<Person> result = jsonTransformer.fromJSonToSuccess(response.body(), Person.class);
                System.out.println("Eliminado:\n" + result.getData());
            } else {
                Result.Error error = jsonTransformer.getError(response.body());
                System.out.println("Error al eliminar: " + error.getError());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
