package es.ieslavereda.client.api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import es.ieslavereda.server.model.entity.Result;

import java.lang.reflect.Type;
import java.util.List;

public class Conversor {
    private static Gson gson;
    private static Conversor conversor;

    public  static Conversor getConversor() {
        if (conversor==null) {
            gson = new Gson();
            conversor=new Conversor();
        }
        return conversor;
    }

    public <T> String toJson(T data){
        String json = gson.toJson(data);
        return json;
    }

    public <T> String toJson(List<T> data){
        String json = gson.toJson(data);
        return json;
    }

    public <T> T fromJson(String json, Class<T> clazz){
        T object = gson.fromJson(json, clazz);
        return object;
    }

    public <T> List<T> fromJsonList(String json, Class<T> clazz){
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        List<T> object = gson.fromJson(json, typeOfT);
        return object;
    }

    public <T> Result.Success<T> fromJSonToSuccess(String json, Class<T> myType) {
        Type dinamicType = TypeToken.getParameterized(Result.Success.class, myType).getType();
        return gson.fromJson(json, dinamicType);
    }

    public Result.Error getError(String json){
        return gson.fromJson(json, Result.Error.class);
    }

}