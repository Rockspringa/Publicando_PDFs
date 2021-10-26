package edu.publishPDF.database.accesors.retroalimentacion;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.revistas.Comentario;

public class ComentariosGetter extends AccesorTools {

    private static final String COMENTARIOS = "SELECT suscriptor, comentario, fecha FROM "
            + "COMENTARIOS WHERE revista = ?";
    
    public static String getComentariosDeRevista(int revistaId) throws SQLException, InvalidInputType {
        List<Comentario> comentarios = new ArrayList<>();

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(COMENTARIOS);

        stmt.setInt(1, revistaId);

        ResultSet res = stmt.executeQuery();

        while (res.next()) {
            String suscriptor = res.getString(1);
            String comentario = res.getString(2);
            Date fecha = res.getDate(3);

            comentarios.add(Comentario.createComentario(revistaId, suscriptor, comentario, fecha));
        }
        
        stmt.close();
        Conexion.closeSession();

        return GSON_FOR_DATE.toJson(comentarios);
    }
    
}
