package es.ieslavereda.client;

import com.google.common.util.concurrent.*;
import com.google.gson.Gson;
import es.ieslavereda.client.api.CallInterface;
import es.ieslavereda.client.api.Connector;
import es.ieslavereda.server.model.API;
import es.ieslavereda.server.model.JsonTransformer;
import es.ieslavereda.server.model.entity.Person;
import es.ieslavereda.server.model.entity.Result;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Handler;

public class AppClient {

    //static JsonTransformer<Person> jsonTransformer = new JsonTransformer<>();

    static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    public static void main(String[] args) {

        // Get all
        getAll();

        // Get by dni
        getByDNI("222");

        // Add a new person
        Person person = new Person("445", "Invent", "Invent Invent", 99);
        addPerson(person);

        // Update person
        person.setApellidos("Inventado Inventado");
        updatePerson(person);

        // Delete person
        delete("445");

    }

//    private static void updatePerson(Person p) {
//
//        HttpClient client = HttpClient.newHttpClient();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON))
//                .PUT(HttpRequest.BodyPublishers.ofString(jsonTransformer.render(p)))
//                .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            if (response.statusCode() == 500) {
//
//                Result.Error result = jsonTransformer.getError(response.body());
//                System.out.println(result.getError());
//
//            } else if (response.statusCode() == 200) {
//
//                Result.Success<Person> result = jsonTransformer.fromJSonToSuccess(response.body(), Person.class);
//                Person person = result.getData();
//                System.out.println("Actualizdo el usuario:\n" + person);
//
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//    }

    private static void updatePerson(Person p) {
        executeCall(new CallInterface() {

            Result<Person> result;

            @Override
            public void doInBackground() {
                result = Connector.getConector().putResult(Person.class, p, API.Routes.PERSON);
            }

            @Override
            public void doInUI() {
                if (result instanceof Result.Success)
                    System.out.println("Actualizado : " + ((Result.Success<?>) result).getData());
                else if (result instanceof Result.Error)
                    System.out.println("UPDATE ERROR: " + ((Result.Error) result).getError());
            }
        });
    }

//    private static void addPerson(Person p) {
//        HttpClient client = HttpClient.newHttpClient();
//
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON))
//                .POST(HttpRequest.BodyPublishers.ofString(jsonTransformer.render(p)))
//                .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            if (response.statusCode() == 500) {
//
//                Result.Error result = jsonTransformer.getError(response.body());
//                System.out.println("Error al añadir:\n" + result.getError());
//
//            } else if (response.statusCode() == 200) {
//
//                Result.Success<Person> result = jsonTransformer.fromJSonToSuccess(response.body(), Person.class);
//                Person person = result.getData();
//                System.out.println("Añadida la persona:\n" + person);
//
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//    }
    private static void addPerson(Person p) {
        executeCall(new CallInterface() {

            Result<Person> result;

            @Override
            public void doInBackground() {
                result = Connector.getConector().postResult(Person.class, p, API.Routes.PERSON);
            }

            @Override
            public void doInUI() {
                if (result instanceof Result.Success)
                    System.out.println("Añadido : " + ((Result.Success<?>) result).getData());
                else if (result instanceof Result.Error)
                    System.out.println("ADD ERROR: " + ((Result.Error) result).getError());
            }
        });
    }

//    private static void getByDNI() {
//
//        String dni = "123";
//        HttpClient client = HttpClient.newHttpClient();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON + "?dni=" + dni))
//                .GET()
//                .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            Person p = jsonTransformer.getObject(response.body(), Person.class);
//            System.out.println("El usurio con dni " + "123" + " es :\n" + p);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private static void getByDNI(String dni) {
        executeCall(new CallInterface() {
            Person p;

            @Override
            public void doInBackground() {
                p = Connector.getConector().get(Person.class, API.Routes.PERSON + "?dni=" + dni);
            }

            @Override
            public void doInUI() {
                System.out.println("Obtenido por dni: " + p);
            }
        });
    }

//    public static void getAll() {
//        HttpClient client = HttpClient.newHttpClient();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON_ALL))
//                .GET()
//                .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            Gson gson = new Gson();
//            Person[] personas = gson.fromJson(response.body(), Person[].class);
//            //Person[] p = jsonTransformer.getObject(response.body().toString(), Person.class);
//            System.out.println("Listado de personas");
//            for (Person p : personas)
//                System.out.println(p);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void getAll() {

        executeCall(new CallInterface() {
            Person[] personas;

            @Override
            public void doInBackground() {
                personas = Connector.getConector().get(Person[].class, API.Routes.PERSON_ALL);
            }

            @Override
            public void doInUI() {
                System.out.println("Obtenidos " + personas.length + ":");
                for (Person p : personas)
                    System.out.println(p);
            }
        });


    }

//    public static void delete(String dni){
//        HttpClient client = HttpClient.newHttpClient();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(API.Routes.SERVER_BASE + API.Routes.PERSON + "?dni=" + dni))
//                .DELETE()
//                .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            if(response.statusCode()==200) {
//                Result.Success<Person> result = jsonTransformer.fromJSonToSuccess(response.body(), Person.class);
//                System.out.println("Eliminado:\n" + result.getData());
//            } else {
//                Result.Error error = jsonTransformer.getError(response.body());
//                System.out.println("Error al eliminar: " + error.getError());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void delete(String dni) {

        executeCall(new CallInterface() {
            Result<Person> result;

            @Override
            public void doInBackground() {
                result = Connector.getConector().deleteResult(Person.class, API.Routes.PERSON + "?dni=" + dni);
            }

            @Override
            public void doInUI() {
                if (result instanceof Result.Success)
                    System.out.println("Eliminado : " + ((Result.Success<?>) result).getData());
                else if (result instanceof Result.Error)
                    System.out.println("DELETE ERROR: " + ((Result.Error) result).getError());
            }
        });
    }

    public static void executeCall(CallInterface callInterface) {

        ListenableFuture listenableFuture = service.submit(() -> callInterface.doInBackground());
        Futures.addCallback(listenableFuture, new FutureCallback<Void>() {
            // we want this handler to run immediately after we push the big red button!
            public void onSuccess(Void explosion) {
                callInterface.doInUI();
            }

            public void onFailure(Throwable thrown) {
                System.out.println(thrown.getMessage());
            }
        }, service);
    }
}
