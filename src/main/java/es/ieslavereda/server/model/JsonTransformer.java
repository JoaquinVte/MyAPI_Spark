package es.ieslavereda.server.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import es.ieslavereda.server.model.entity.Result;
import spark.ResponseTransformer;

import java.lang.reflect.Type;

public class JsonTransformer<T> implements ResponseTransformer {

    private final Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    public T getObject(String json, Class<T> classOf){ return gson.fromJson(json,classOf); }

    public Result.Success<T> fromJSonToSuccess(String json, Class<T> myType) {
        Type dinamicType = TypeToken.getParameterized(Result.Success.class, myType).getType();
        return gson.fromJson(json, dinamicType);
    }

    public Result.Error getError(String json){
        return gson.fromJson(json, Result.Error.class);
    }

}
