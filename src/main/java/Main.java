import xson.JsonStringConverter;

/**
 * Created by pablo on 30/10/16.
 */
public class Main{

    public static void main(String [] args)
    {
        System.out.println(JsonStringConverter.jsonToString(JsonStringConverter.stringToJson("{\"a\": \"A\"}")));
    }
}
