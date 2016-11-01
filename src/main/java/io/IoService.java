package io;

import xson.WholePathConverter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by pablo on 1/11/16.
 */
public class IoService {

    public static void readJsonWriteBson(String originalFile, String newFile)
    {
        try {
            String jsonText = Files.lines(Paths.get(originalFile)).reduce(String::concat).orElseGet(() -> null);
            if(jsonText==null)
                throw new IOException("File doesn't seem to exist...");

            byte [] bsonBytes = WholePathConverter.jsonToBson(jsonText);

            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(bsonBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBsonWriteJson(String originalFile, String newFile)
    {
        BufferedWriter writer = null;
        try {
            byte [] bsonBytes = Files.readAllBytes(Paths.get(originalFile));
            if(bsonBytes==null)
                throw new IOException("File doesn't seem to exist...");

            String jsonText = WholePathConverter.bsonToJson(bsonBytes);

            writer = new BufferedWriter(new FileWriter(newFile));
            writer.write(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }

    }
}
