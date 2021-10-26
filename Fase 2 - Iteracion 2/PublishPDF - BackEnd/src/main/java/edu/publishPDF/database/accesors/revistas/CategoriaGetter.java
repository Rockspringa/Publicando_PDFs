package edu.publishPDF.database.accesors.revistas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;

public class CategoriaGetter extends AccesorTools {
    
    private static final String ALL = "SELECT nombre FROM CATEGORIA";

    public static String findAllCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(ALL);
        ResultSet res = stmt.executeQuery();

        while (res.next())
            categories.add(res.getString(1));

        stmt.close();
        Conexion.closeSession();

        return GSON.toJson(categories);
    }
}
