import io.IoService;

/**
 * Created by pablo on 30/10/16.
 */
public class Main{

    public static void main(String [] args)
    {
        if(args.length != 1)
        {
            System.out.println("Wrong number of parameters");
            return;
        }
        doMain(args[0]);

    }

    public static void doMain(String fileName)
    {
        if(fileName.endsWith(".json"))
        {
            IoService.readJsonWriteBson(fileName, fileName.substring(0,fileName.lastIndexOf(".")) + ".bson");
        }

        if(fileName.endsWith(".bson"))
        {
            IoService.readBsonWriteJson(fileName, fileName.substring(0,fileName.lastIndexOf(".")) + ".json");
        }
    }



}
