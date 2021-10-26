package edu.publishPDF.database.accesors.revistas;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.database.accesors.user.ExtrasSetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.revistas.NumeroRevista;
import edu.publishPDF.model.revistas.Revista;

public class RevistaSetter extends AccesorTools {

    private static final String POST_REVISTA = "INSERT INTO REVISTA (editor, nombre, descripcion, "
            + "fecha_publicada, categoria, coste_mes) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String POST_NUMERO = "INSERT INTO NUMERO_REVISTA VALUES (?, ?, ?)";

    private static Revista toRevistaValidada(String json) throws InvalidInputType {
        Revista revista = GSON_FOR_DATE.fromJson(json, Revista.class);
        revista = Revista.createRevista(revista.getId(), revista.getEditor(), revista.getFechaPublicacion(),
                revista.getNombre(), revista.getCosteMes(), revista.getCategoria(), revista.getDescripcion(),
                revista.isComentariosActivos(), revista.isMeGustasActivos(), revista.isSuscripcionesActivas());

        return revista;
    }

    public static boolean publishRevista(String json) throws InvalidInputType, SQLException {
        Revista revista = toRevistaValidada(json);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(POST_REVISTA);

        stmt.setString(1, revista.getEditor());
        stmt.setString(2, revista.getNombre());
        stmt.setString(3, revista.getDescripcion());
        stmt.setDate(4, Date.valueOf(revista.getFechaPublicacion()));
        stmt.setString(5, revista.getCategoria());
        stmt.setDouble(6, revista.getCosteMes());

        int rows = stmt.executeUpdate();

        stmt.close();

        String[] etiquetas = revista.getEtiquetas();
        if (etiquetas != null) {
            ExtrasSetter.updateRevistaTags(revista.getId(), etiquetas);
        }

        Conexion.closeSession();

        return rows > 0;
    }

    public static boolean publishNumero(int numero, int revista, String archivo) throws SQLException, InvalidInputType {
        NumeroRevista numeroR = NumeroRevista.createNumeroRevista(numero, revista, archivo);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(POST_NUMERO);

        stmt.setInt(1, numeroR.getId());
        stmt.setInt(2, numeroR.getRevista());
        stmt.setString(3, numeroR.getArchivo());

        int rows = stmt.executeUpdate();

        stmt.close();
        Conexion.closeSession();

        return rows > 0;
    }
}
