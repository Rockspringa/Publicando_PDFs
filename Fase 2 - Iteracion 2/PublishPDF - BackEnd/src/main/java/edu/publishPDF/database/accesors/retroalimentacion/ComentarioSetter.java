package edu.publishPDF.database.accesors.retroalimentacion;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.revistas.Comentario;

public class ComentarioSetter extends AccesorTools {

    private static final String PUBLICAR_COMENTARIO = "INSERT INTO COMENTARIOS VALUES (?, ?, ?, ?) "
            + "ON DUPlICATE KEY UPDATE comentario = ?, fecha = ?";

    private static Comentario toComentarioValidado(String json) throws InvalidInputType {
        Comentario comentario = GSON_FOR_DATE.fromJson(json, Comentario.class);

        comentario = Comentario.createComentario(comentario.getRevista(), comentario.getSuscriptor(),
                comentario.getComentario(), comentario.getFecha()); // validacion

        return comentario;
    }

    public static boolean comentar(String json) throws SQLException, InvalidInputType {
        Comentario comentario = toComentarioValidado(json);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(PUBLICAR_COMENTARIO);

        stmt.setInt(1, comentario.getRevista());
        stmt.setString(2, comentario.getSuscriptor());
        stmt.setString(3, comentario.getComentario());
        stmt.setDate(4, Date.valueOf(comentario.getFecha()));
        stmt.setString(5, comentario.getComentario());
        stmt.setDate(6, Date.valueOf(comentario.getFecha()));
        
        int rows = stmt.executeUpdate();

        stmt.close();
        Conexion.closeSession();

        return rows > 0;
    }
}
