package xson;

import com.google.gson.*;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

                if(object instanceof ObjectId)
                {
                    ObjectId objectId = (ObjectId) object;

                    JsonObject result = new JsonObject();
                    result.add("$oid",new JsonPrimitive(objectId.toString()));
                    return result;
                }


                if(object instanceof Date)
                {
                    Date date = (Date) object;

                    SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));

                    JsonObject result = new JsonObject();
                    result.add("$date",new JsonPrimitive(sdf.format(date)));
                    return result;
                }
            }
        }
         return null;
    }




}
