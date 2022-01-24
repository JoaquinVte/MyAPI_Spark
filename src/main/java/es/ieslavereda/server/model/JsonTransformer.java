package es.ieslavereda.server.model;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonTransformer<T> implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    public T getObject(String json, Class<T> classOf){
        return gson.fromJson(json,classOf);
    }

}
