package xson;

import com.google.gson.*;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.BSONObject;
import org.bson.types.ObjectId;

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
            ObjectId result = objectIdToBson(json.getAsJsonObject());
            if(result==null)
                return jsonObjectToBson(json.getAsJsonObject());
            else
                return result;
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
