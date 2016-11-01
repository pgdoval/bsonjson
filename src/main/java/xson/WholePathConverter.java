package xson;

import com.google.gson.JsonElement;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

/**
 * Created by pablo on 1/11/16.
 */
public class WholePathConverter {

    public static String bsonToJson(byte [] bson) throws Exception {
        BSONObject bsonObject = BsonByteConverter.binaryToBson(bson);
        JsonElement jsonElement = null;
        if(bsonObject instanceof BasicBSONObject)
            jsonElement = BsonJsonConverter.bsonToJson((BasicBSONObject) bsonObject);
        if(bsonObject instanceof BasicBSONList)
            jsonElement = BsonJsonConverter.bsonToJson((BasicBSONList) bsonObject);

        if(jsonElement == null)
            throw new Exception("bson is not BasicBsonObject or BasicBSONList");

        return JsonStringConverter.jsonToString(jsonElement);
    }

    public static byte[] jsonToBson(String json){
        JsonElement jsonElement = JsonStringConverter.stringToJson(json);
        BSONObject bsonObject = JsonBsonConverter.jsonToBson(jsonElement);
        return BsonByteConverter.bsonToBinary(bsonObject);
    }

}
