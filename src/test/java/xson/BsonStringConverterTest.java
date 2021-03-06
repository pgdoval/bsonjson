package xson;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by pablo on 30/10/16.
 */
public class BsonStringConverterTest {


    private BSONObject object;
    private BSONObject innerObject;
    private BasicDBList list;

    @Before
    public void setUp() throws Exception {
        innerObject = new BasicDBObject();
        innerObject.put("cadena", "Inner");

        list = new BasicDBList();
        list.add(new BasicDBObject());

        object = new BasicDBObject();
        object.put("cadena", "Hello");
        object.put("entero", 1);
        object.put("object", innerObject);
        object.put("lista", list);
        object.put("objectId", ObjectId.get());
        object.put("objectId", new Date());
    }

    //We can only test idempotency because we can't simulate the binary format
    @Test
    public void idempotency() throws Exception {

        assert(object.equals(BsonByteConverter.binaryToBson(BsonByteConverter.bsonToBinary(object))));

    }

}