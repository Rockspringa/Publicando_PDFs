package edu.publishPDF.database.accesors.revistas;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.revistas.Revista;

public class RevistaGetter extends AccesorTools {

    private static final String BUSCAR = "SELECT revista, editor, fecha_publicada, nombre, categoria"
            + " FROM ETIQUETAS_REVISTA JOIN REVISTA ON revista = revista_id WHERE etiqueta"
            + " LIKE CONCAT('%', ?, '%') OR categoria LIKE CONCAT('%', ?, '%') GROUP BY revista";

    private static final String REVISTA = "SELECT editor, fecha_publicada, nombre, categoria,"
            + " coste_mes, suscripciones_activas FROM REVISTA WHERE revista_id = ?";

    public static String searchRevista(String criterio) throws SQLException, InvalidInputType {

        List<Revista> coincidencias = new ArrayList<>();

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(BUSCAR);

        stmt.setString(1, criterio);
        stmt.setString(2, criterio);

        ResultSet res = stmt.executeQuery();

        while (res.next()) {
            int id = res.getInt(1);
            String editor = res.getString(2);
            Date fechaPublicacion = res.getDate(3);
            String nombre = res.getString(4);
            String categoria = res.getString(5);

            coincidencias.add(Revista.createRevista(id, editor, fechaPublicacion, nombre, categoria));
        }

        stmt.close();
        Conexion.closeSession();

        return GSON_FOR_DATE.toJson(coincidencias);
    }

    public static String getRevista(int revistaId) throws SQLException, InvalidInputType {
        Revista revista = null;

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(REVISTA);

        stmt.setInt(1, revistaId);

        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            String editor = res.getString(1);
            Date fechaPublicacion = res.getDate(2);
            String nombre = res.getString(3);
            String categoria = res.getString(4);
            double costoMes = res.getDouble(5);
            boolean suscripcionesActivas = res.getBoolean(6);
            suscripcionesActivas = (res.wasNull()) ? true : suscripcionesActivas;

            revista = Revista.createRevista(revistaId, editor, fechaPublicacion, nombre, categoria, costoMes, null,
                    null, null, suscripcionesActivas);
        }

        stmt.close();
        Conexion.closeSession();

        return (revista != null) ? GSON_FOR_DATE.toJson(revista) : null;
    }

}
