package xson;


import org.bson.BSON;
import org.bson.BSONObject;

/**
 * Created by pablo on 30/10/16.
 */
public class BsonStringConverter {


    public static BSONObject binaryToBson(byte [] fileContent) {
        return BSON.decode(fileContent);
    }


    public static byte[] bsonToBinary(BSONObject bson) {
        return BSON.encode(bson);
    }
}
