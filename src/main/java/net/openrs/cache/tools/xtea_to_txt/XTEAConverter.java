package net.openrs.cache.tools.xtea_to_txt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.openrs.cache.tools.JsonLoader;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ethan Kyle Millard <skype:pumpklins>
 * @since June 06, 2017 @ 10:51 AM
 */
public class XTEAConverter {


    private static final String XTEA_JSON_PATH = "./repository/xtea/json/xteas.json";

    private static final String XTEA_TXT_PATH = "./repository/xtea/txt/";

    public static void main(String[] args) throws Exception {
        parse().load();
        System.out.println("Completed the conversion of " + XTEAS_LOADED + " XTEAS.");
    }

    public static int XTEAS_LOADED;

    public static JsonLoader parse() {
        XTEAS_LOADED = 0;
        return new JsonLoader() {
            @Override
            public void load(JsonObject reader, Gson builder) {
                String region = reader.get("mapsquare").getAsString();
                int[] keys = builder.fromJson(reader.get("key").getAsJsonArray(), int[].class);

                try{
                    PrintWriter writer = new PrintWriter(XTEA_TXT_PATH + region + ".txt", "UTF-8");

                    for (int key : keys) {
                        writer.println(key);
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                XTEAS_LOADED++;

            }

            @Override
            public String filePath() {
                return XTEA_JSON_PATH;
            }
        };
    }

}