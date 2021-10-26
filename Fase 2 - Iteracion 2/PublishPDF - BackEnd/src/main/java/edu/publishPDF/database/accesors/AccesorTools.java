package edu.publishPDF.database.accesors;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.revistas.Suscripcion;
import edu.publishPDF.tools.gsonTools.DateAdapters;

public class AccesorTools {

    protected static final Gson GSON = new Gson();
    protected static final Gson GSON_FOR_DATE = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new DateAdapters.DateAdapter())
            .registerTypeAdapter(LocalDate.class, new DateAdapters.DateReceivedAdapter())
            .create();

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

    protected static Suscripcion toSuscripcionValidada(String json) throws InvalidInputType {
        Suscripcion suscripcion = GSON_FOR_DATE.fromJson(json, Suscripcion.class);

        suscripcion = Suscripcion.createSuscripcion(suscripcion.getRevista(), suscripcion.getSuscriptor(),
                suscripcion.getFechaSuscripcion(), suscripcion.isMensual()); // validacion de campos

        return suscripcion;
    }

}
