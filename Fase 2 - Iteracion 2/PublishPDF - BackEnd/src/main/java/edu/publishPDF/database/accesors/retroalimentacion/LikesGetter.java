package edu.publishPDF.database.accesors.retroalimentacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.revistas.Suscripcion;

public class LikesGetter extends AccesorTools {
    
    private static final String LIKED = "SELECT fecha FROM ME_GUSTAS WHERE revista = ? AND suscriptor = ?";
    
    public static LocalDate dateLiked(int revista, String suscriptor) throws InvalidInputType, SQLException {
        LocalDate date = null;
        Suscripcion suscripcion = Suscripcion.createSuscripcion(revista, suscriptor);
        
        Conexion.createSession();
        PreparedStatement stmt = Conexion.getPrepareStatement(LIKED);

        stmt.setInt(1, suscripcion.getRevista());
        stmt.setString(2, suscripcion.getSuscriptor());

        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            date = res.getDate(1).toLocalDate();
        }

        stmt.close();
        Conexion.closeSession();

        return date;
    }
}
