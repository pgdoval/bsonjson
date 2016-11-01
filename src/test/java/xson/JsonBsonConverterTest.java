package xson;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by pablo on 1/11/16.
 */
public class JsonBsonConverterTest {

    private JsonArray jsonArray;
    private JsonObject jsonObject;
    private JsonObject jsonObjectId;

    private BasicDBList bsonArray;
    private BasicDBObject bsonObject;
    private ObjectId objectId;

    @Before
    public void setUp() throws Exception {

        String id = "21e012ef3291085486698ab1";
        Date now = new Date();



        //BSON

        //Array
        bsonArray= new BasicDBList();

        BasicDBObject innerBson2 = new BasicDBObject();
        innerBson2.put("age", 29);

        bsonArray.add(innerBson2);
        bsonArray.add("primitive");
        bsonArray.add(null);


        //Object, which contains the array
        bsonObject= new BasicDBObject();

        BasicDBObject innerBson1 = new BasicDBObject();
        innerBson1.put("name", "Pablo");

        bsonObject.put("basicProps", innerBson1);
        bsonObject.put("extraProps", bsonArray);

        //ObjectId

        objectId = new ObjectId(id);
        bsonObject.put("_id", objectId);

        //Date

        bsonObject.put("date", now);


        //JSON

        //Array
        jsonArray= new JsonArray();

        JsonObject innerJson2 = new JsonObject();
        innerJson2.add("age", new JsonPrimitive(29));

        jsonArray.add(innerJson2);
        jsonArray.add(new JsonPrimitive("primitive"));
        jsonArray.add(JsonNull.INSTANCE);


        //Object, which contains the array
        jsonObject= new JsonObject();

        JsonObject innerJson1 = new JsonObject();
        innerJson1.add("name", new JsonPrimitive("Pablo"));

        jsonObject.add("basicProps", innerJson1);
        jsonObject.add("extraProps", jsonArray);

        //ObjectId
        jsonObjectId = new JsonObject();
        jsonObjectId.add("$oid",new JsonPrimitive(id));
        jsonObject.add("_id", jsonObjectId);

        //Date
        JsonObject jsonDate = new JsonObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));

        jsonDate.add("$date", new JsonPrimitive(dateFormat.format(now)));
        jsonObject.add("date", jsonDate);

    }

    @Test
    public void jsonToBson() throws Exception {
        assertEquals(bsonObject,JsonBsonConverter.jsonToBson(jsonObject));
        assertEquals(bsonArray,JsonBsonConverter.jsonToBson(jsonArray));

        assertEquals(jsonArray,BsonJsonConverter.bsonToJson(bsonArray));
        assertEquals(jsonObject,BsonJsonConverter.bsonToJson(bsonObject));

    }

}