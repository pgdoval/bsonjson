package xson;

import com.google.gson.*;
import com.google.gson.internal.LazilyParsedNumber;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.BSONObject;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
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
        {
            /*
            * It's necessary to unwrap the number manually, because otherwise it can be a
            * LazilyParsedNumber, if the json number comes from a gson transformation.
            * That's why we need to convert it another number which contains the real
            * unwrapped number with its actual data type - be it int, long or double.
            * */
            Number n = json.getAsNumber();
            Number unwrapped;

            if (n instanceof LazilyParsedNumber) {
                LazilyParsedNumber lpn = (LazilyParsedNumber) n;
                BigDecimal bigDecimal = new BigDecimal(lpn.toString());
                if (bigDecimal.scale() <= 0) {
                    if (bigDecimal.compareTo(new BigDecimal(Integer.MAX_VALUE)) <= 0) {
                        unwrapped = bigDecimal.intValue();
                    } else {
                        unwrapped = bigDecimal.longValue();
                    }
                } else {
                    unwrapped = bigDecimal.doubleValue();
                }
            } else {
                unwrapped = n;
            }
            return unwrapped;
        }

        if(json.isString())
            return json.getAsString();

        return null;
    }



}
