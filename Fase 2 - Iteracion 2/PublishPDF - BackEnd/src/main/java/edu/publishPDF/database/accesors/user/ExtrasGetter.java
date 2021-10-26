package edu.publishPDF.database.accesors.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;

public class ExtrasGetter extends AccesorTools {
    
    private static final String TAGS = "SELECT nombre FROM ETIQUETA";
    private static final String USER_TAGS = "SELECT etiqueta FROM ETIQUETAS_SUSCRIPTOR WHERE suscriptor = ?";

    private static List<String> getAllTags() throws SQLException {
        List<String> tags = new ArrayList<>();
        
        PreparedStatement stmt = Conexion.getPrepareStatement(TAGS);
        ResultSet res = stmt.executeQuery();

        while (res.next())
            tags.add(res.getString(1));
        
        stmt.close();

        return tags;
    }

    public static String getAllAndUserTags(String username) throws SQLException {
        List<List<String>> conj = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        List<String> esco = new ArrayList<>();
        
        Conexion.createSession();

        tags = getAllTags();

        PreparedStatement stmt = Conexion.getPrepareStatement(USER_TAGS);

        stmt.setString(1, username);

        ResultSet res = stmt.executeQuery();

        while (res.next())
            esco.add(res.getString(1));
        
        stmt.close();
        Conexion.closeSession();
        Conexion.closeSession();

        conj.add(tags);
        conj.add(esco);

        return GSON.toJson(conj);
    }
}
