package edu.publishPDF.database.accesors;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.publishPDF.tools.gsonTools.DateAdapter;

public class AccesorTools {

    protected static final Gson GSON = new Gson();
    protected static final Gson GSON_FOR_DATE = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new DateAdapter()).create();

    protected static String toCsv(boolean firstComma, String... args) {
        StringBuilder sb = new StringBuilder((firstComma) ? ", " : "");
        
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && !args[i].equals("")) {
                sb.append(args[i]);

                if (i != args.length - 1) {
                    sb.append(", ");
                }
            }
        }
        return (args.length != 0) ? sb.toString() : "";
    }

    public static String[] cookieJsonToData(String json) {
        JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();

        String[] out = new String[3];
        out[0] = jsonObj.get("username").getAsString();
        out[1] = jsonObj.get("nombre").getAsString();
        out[2] = jsonObj.get("type").getAsString();

        return out;
    }

}
