package edu.publishPDF.database.accesors.retroalimentacion;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.revistas.Suscripcion;

public class LikesSetter extends AccesorTools {
    
    private static final String DAR_LIKE = "INSERT INTO ME_GUSTAS VALUES (?, ?, ?)";
    private static final String QUITAR_LIKE = "DELETE FROM ME_GUSTAS WHERE revista = ? AND suscriptor = ?";

    private static int darLikeRealmente(Suscripcion suscripcion) throws SQLException {
        PreparedStatement stmt = Conexion.getPrepareStatement(DAR_LIKE);

        stmt.setInt(1, suscripcion.getRevista());
        stmt.setString(2, suscripcion.getSuscriptor());
        stmt.setDate(3, Date.valueOf(LocalDate.now()));

        int rows = stmt.executeUpdate();
        stmt.close();

        return rows;
    }

    private static int qutarLike(Suscripcion suscripcion) throws SQLException {
        PreparedStatement stmt = Conexion.getPrepareStatement(QUITAR_LIKE);

        stmt.setInt(1, suscripcion.getRevista());
        stmt.setString(2, suscripcion.getSuscriptor());

        int rows = stmt.executeUpdate();
        stmt.close();

        return rows;
    }

    public static boolean darLike(String json) throws InvalidInputType, SQLException {
        Suscripcion suscripcion = toSuscripcionValidada(json);
        boolean liked = false;

        Conexion.createSession();
        if (LikesGetter.dateLiked(suscripcion.getRevista(), suscripcion.getSuscriptor()) == null) {
            darLikeRealmente(suscripcion);
            liked = true;
        } else {
            qutarLike(suscripcion);
            liked = false;
        }
        Conexion.closeSession();
        
        return liked;
    }
}
