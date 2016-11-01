package xson;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pablo on 1/11/16.
 */
public class WholePathConverterTest {
    @Test
    public void idempotency() throws Exception {

        String json = "{\"_id\":{\"$oid\":\"21e012ef3291085486698ab1\"},\"basicProps\":{\"name\":\"Pablo\"},\"extraProps\":[{\"age\":29},\"primitive\",null],\"date\":{\"$date\":\"2016-11-01T17:03:39.041Z\"}}";

        //We check the idempotency twice just to see if there is any way that it's failing
        assertEquals(json,WholePathConverter.bsonToJson(WholePathConverter.jsonToBson(WholePathConverter.bsonToJson(WholePathConverter.jsonToBson(json)))));
    }

}