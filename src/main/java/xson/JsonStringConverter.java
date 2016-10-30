package xson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by pablo on 30/10/16.
 */
public class JsonStringConverter {

    public static JsonElement stringToJson(String content) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson (content, JsonElement.class);
    }


    public static String jsonToString(JsonElement json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(json);
    }

}
