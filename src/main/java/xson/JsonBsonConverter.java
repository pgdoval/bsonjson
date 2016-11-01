package xson;

import com.google.gson.*;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.BSONObject;
import org.bson.types.ObjectId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by pablo on 1/11/16.
 */
public class JsonBsonConverter {

    public static BSONObject jsonToBson(JsonElement json)
    {
        if(json.isJsonArray())
            return jsonArrayToBson(json.getAsJsonArray());
        if(json.isJsonObject())
            return jsonObjectToBson(json.getAsJsonObject());
        return null;
    }

    private static Object innerJsonToBson(JsonElement json)
    {
        if(json.isJsonArray())
            return jsonArrayToBson(json.getAsJsonArray());
        if(json.isJsonNull())
            return jsonNullToBson(json.getAsJsonNull());
        if(json.isJsonObject())
        {
            ObjectId objectId = objectIdToBson(json.getAsJsonObject());
            if(objectId!=null)
                return objectId;

            Date date = dateToBson(json.getAsJsonObject());
            if(date!=null)
                return date;

            return jsonObjectToBson(json.getAsJsonObject());
        }
        if(json.isJsonPrimitive())
            return jsonPrimitiveToBson(json.getAsJsonPrimitive());
        return null;
    }

    private static ObjectId objectIdToBson(JsonObject json)
    {
        if(json.entrySet().size()==1 && json.get("$oid")!=null && json.get("$oid") instanceof JsonPrimitive)
            return new ObjectId(json.get("$oid").getAsString());
        else
            return null;
    }

    private static Date dateToBson(JsonObject json)
    {
        if(json.entrySet().size()==1 && json.get("$date")!=null && json.get("$date") instanceof JsonPrimitive)
            try {
                SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
                return sdf.parse(json.get("$date").getAsString());
            } catch (ParseException e) {
                return null;
            }
        else
            return null;
    }

    private static BSONObject jsonArrayToBson(JsonArray json)
    {
        BasicDBList result = new BasicDBList();
        json.forEach(child -> result.add(innerJsonToBson(child)));

        return result;
    }

    private static BSONObject jsonNullToBson(JsonNull json)
    {
        return null;
    }

    private static BSONObject jsonObjectToBson(JsonObject json)
    {
        BasicDBObject result = new BasicDBObject();
        json.entrySet().stream().forEach(entry ->
            result.put(entry.getKey(),innerJsonToBson(entry.getValue())));

        return result;
    }

    private static Object jsonPrimitiveToBson(JsonPrimitive json)
    {
        if(json.isBoolean())
            return json.getAsBoolean();

        if(json.isNumber())
            return json.getAsNumber();

        if(json.isString())
            return json.getAsString();

        return null;
    }



}
