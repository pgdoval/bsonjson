package xson;

import com.google.gson.*;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

/**
 * Created by pablo on 1/11/16.
 */
public class BsonJsonConverter {

    public static JsonElement bsonToJson(BasicBSONObject bson)
    {
        JsonObject result = new JsonObject();

        bson.entrySet().stream().forEach(entry -> {
            result.add(entry.getKey(), objectToJson(entry.getValue()));
        });

        return result;
    }

    public static JsonElement bsonToJson(BasicBSONList bson)
    {
        JsonArray result = new JsonArray();
        bson.stream().forEach(element -> result.add(objectToJson(element)));

        return result;
    }

    private static JsonElement objectToJson(Object object)
    {
        if(object instanceof BasicBSONList )
            return bsonToJson((BasicBSONList)object);
        else {
            if (object instanceof BasicBSONObject)
                return bsonToJson((BasicBSONObject) object);
            else
            {
                if(object==null)
                    return JsonNull.INSTANCE;

                if(object instanceof Boolean)
                    return new JsonPrimitive((Boolean)object);

                if(object instanceof Character)
                    return new JsonPrimitive((Character)object);

                if(object instanceof Number)
                    return new JsonPrimitive((Number)object);

                if(object instanceof String)
                    return new JsonPrimitive((String)object);
            }
        }
         return null;
    }




}
