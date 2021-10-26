package edu.publishPDF.database.accesors.revistas;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.revistas.Suscripcion;

public class SuscripcionCreator extends AccesorTools {
    
    private static final String SUSCRIBIRSE = "INSERT INTO SUSCRIPCIONES VALUES (?, ?, ?, ?)";

    public static boolean suscribirseRevista(String json) throws InvalidInputType, SQLException {
        Suscripcion suscripcion = toSuscripcionValidada(json);
        
        Conexion.createSession();
        PreparedStatement stmt = Conexion.getPrepareStatement(SUSCRIBIRSE);

        stmt.setInt(1, suscripcion.getRevista());
        stmt.setString(2, suscripcion.getSuscriptor());
        stmt.setDate(3, Date.valueOf(suscripcion.getFechaSuscripcion()));
        stmt.setBoolean(4, suscripcion.isMensual());

        int rows = stmt.executeUpdate();

        stmt.close();
        Conexion.closeSession();

        return rows > 0;
    }
}
