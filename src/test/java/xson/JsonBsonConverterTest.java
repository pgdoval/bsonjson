package xson;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pablo on 1/11/16.
 */
public class JsonBsonConverterTest {

    private JsonArray jsonArray;
    private JsonObject jsonObject;

    private BasicDBList bsonArray;
    private BasicDBObject bsonObject;


    @Before
    public void setUp() throws Exception {

        jsonArray= new JsonArray();

        JsonObject innerJson2 = new JsonObject();
        innerJson2.add("age", new JsonPrimitive(29));

        jsonArray.add(innerJson2);
        jsonArray.add(new JsonPrimitive("primitive"));
        jsonArray.add(JsonNull.INSTANCE);


        jsonObject= new JsonObject();

        JsonObject innerJson1 = new JsonObject();
        innerJson1.add("name", new JsonPrimitive("Pablo"));

        jsonObject.add("basicProps", innerJson1);
        jsonObject.add("extraProps", jsonArray);

        bsonArray= new BasicDBList();

        BasicDBObject innerBson2 = new BasicDBObject();
        innerBson2.put("age", 29);

        bsonArray.add(innerBson2);
        bsonArray.add("primitive");
        bsonArray.add(null);


        bsonObject= new BasicDBObject();

        BasicDBObject innerBson1 = new BasicDBObject();
        innerBson1.put("name", "Pablo");

        bsonObject.put("basicProps", innerBson1);
        bsonObject.put("extraProps", bsonArray);


    }

    @Test
    public void jsonToBson() throws Exception {
        assertEquals(bsonObject,JsonBsonConverter.jsonToBson(jsonObject));
        assertEquals(bsonArray,JsonBsonConverter.jsonToBson(jsonArray));
        assertEquals(jsonArray,BsonJsonConverter.bsonToJson(bsonArray));
        assertEquals(jsonObject,BsonJsonConverter.bsonToJson(bsonObject));
    }

}