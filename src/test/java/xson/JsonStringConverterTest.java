package xson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import junit.framework.TestCase;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by pablo on 30/10/16.
 */
public class JsonStringConverterTest{

    private JsonObject simpleObject;
    private JsonArray arrayObject;
    private JsonObject compoundObject;

    protected void setUp() throws Exception
    {
        simpleObject = new JsonObject();
        simpleObject.add("a",new JsonPrimitive("A"));
        simpleObject.add("b",new JsonPrimitive("B"));

        arrayObject = new JsonArray();
        arrayObject.add(simpleObject);

        compoundObject = new JsonObject();
        compoundObject.add("c", simpleObject);
    }

    @org.junit.Test
    public void stringToJson() throws Exception {
        setUp();
        assertEquals(JsonStringConverter.stringToJson("{\"a\": \"A\", \"b\": \"B\"}"), simpleObject);
        assertEquals(JsonStringConverter.stringToJson("[{\"a\": \"A\", \"b\": \"B\"}]"), arrayObject);
        assertEquals(JsonStringConverter.stringToJson("{\"c\": {\"a\": \"A\", \"b\": \"B\"}}"), compoundObject);

    }

    @org.junit.Test
    public void jsonToString() throws Exception {
        setUp();
        assertEquals(JsonStringConverter.jsonToString(simpleObject), "{\"a\":\"A\",\"b\":\"B\"}");
        assertEquals(JsonStringConverter.jsonToString(arrayObject), "[{\"a\":\"A\",\"b\":\"B\"}]");
        assertEquals(JsonStringConverter.jsonToString(compoundObject), "{\"c\":{\"a\":\"A\",\"b\":\"B\"}}");
    }

}