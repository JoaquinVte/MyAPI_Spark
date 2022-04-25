package es.ieslavereda.client.api;


import java.io.IOException;
import java.util.List;

import es.ieslavereda.config.MyConfig;
import es.ieslavereda.server.model.entity.Result;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class Connector {

    private static Connector connector;
    private static Conversor conversor;
    private static CallMethods callMethodsObject;

    public static Connector getConector() {
        if (connector == null) {
            connector = new Connector();
            conversor = Conversor.getConversor();
            callMethodsObject = CallMethods.getCallMethodsObject();
        }
        return connector;
    }

    public <T> List<T> getAsList(Class<T> clazz, String path) {
        String url = MyConfig.getInstance().getBaseURL() + path;
        String jsonResponse = callMethodsObject.get(url);
        if (jsonResponse != null)
            return conversor.fromJsonList(jsonResponse, clazz);
        return null;
    }


    public <T> T get(Class<T> clazz, String path) {
        String url = MyConfig.getInstance().getBaseURL() + path;
        String jsonResponse = callMethodsObject.get(url);
        if (jsonResponse != null)
            return conversor.fromJson(jsonResponse, clazz);
        return null;
    }

    public <T> Result<T> getResult(Class<T> clazz, String path) {
        try {
            String url = MyConfig.getInstance().getBaseURL() + path;
            Response<ResponseBody> jsonResponse = callMethodsObject.getResult(url);
            if (jsonResponse != null && jsonResponse.code() == 200)
                return conversor.fromJSonToSuccess(jsonResponse.body().string(), clazz);
            else if (jsonResponse != null)

                return conversor.getError(jsonResponse.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> Result<T> postResult(Class<T> clazz, T data, String path) {
        try {
            String url = MyConfig.getInstance().getBaseURL() + path;
            String jsonObject = conversor.toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject);
            Response<ResponseBody> jsonResponse = callMethodsObject.postResult(url, body);

            if (jsonResponse != null && jsonResponse.code() == 200)
                return conversor.fromJSonToSuccess(jsonResponse.body().string(), clazz);
            else if (jsonResponse != null)
                return conversor.getError(jsonResponse.errorBody().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> Result<T> putResult(Class<T> clazz, T data, String path) {
        try {
            String url = MyConfig.getInstance().getBaseURL() + path;
            String jsonObject = conversor.toJson(data);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject);
            Response<ResponseBody> jsonResponse = callMethodsObject.putResult(url, body);

            if (jsonResponse != null && jsonResponse.code() == 200)
                return conversor.fromJSonToSuccess(jsonResponse.body().string(), clazz);
            else if (jsonResponse != null)
                return conversor.getError(jsonResponse.errorBody().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> Result<T> deleteResult(Class<T> clazz, String path) {
        try {
            String url = MyConfig.getInstance().getBaseURL() + path;
            Response<ResponseBody> jsonResponse = callMethodsObject.deleteResult(url);
            if (jsonResponse != null && jsonResponse.code() == 200)
                return conversor.fromJSonToSuccess(jsonResponse.body().string(), clazz);
            else if (jsonResponse != null) {
                return conversor.getError(jsonResponse.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}