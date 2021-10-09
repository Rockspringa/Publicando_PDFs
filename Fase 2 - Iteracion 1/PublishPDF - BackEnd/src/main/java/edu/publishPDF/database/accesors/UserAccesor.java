package edu.publishPDF.database.accesors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserAccesor {

    protected static final Gson GSON = new Gson();

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
