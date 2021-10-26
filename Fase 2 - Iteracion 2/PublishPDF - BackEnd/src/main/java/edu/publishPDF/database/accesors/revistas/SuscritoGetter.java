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
import edu.publishPDF.model.errores.TooManyArgumentsException;
import edu.publishPDF.model.revistas.Revista;
import edu.publishPDF.model.revistas.Suscripcion;
import edu.publishPDF.model.users.UserType;
import edu.publishPDF.model.users.types.UserFactory;

public class SuscritoGetter extends AccesorTools {

    private static final String SUSCRIPCIONES = "SELECT revista, editor, fecha_publicada, nombre, categoria"
            + " FROM SUSCRIPCIONES JOIN REVISTA ON revista = revista_id WHERE suscriptor = ?";

    private static final String REVISTA_SUSCRITO = "SELECT revista, editor, fecha_publicada, nombre, categoria,"
            + " coste_mes, descripcion, comentarios_activos, me_gusta_activos, suscripciones_activas FROM "
            + "SUSCRIPCIONES JOIN REVISTA ON revista = revista_id WHERE suscriptor = ? AND revista = ?";

    private static final String REVISTA_NO_SUSCRITO = "SELECT revista_id, editor, fecha_publicada, nombre,"
            + " categoria, descripcion, suscripciones_activas FROM " + "REVISTA WHERE revista_id = ?";

    private static final String ME_GUSTAS_REVISTA = "SELECT COUNT(*) FROM ME_GUSTAS WHERE revista = ?";

    private static final String CANT_NUMEROS_REVISTA = "SELECT COUNT(*) FROM NUMERO_REVISTA WHERE revista = ?";

    private static final String ETIQUETAS_REVISTA = "SELECT etiqueta FROM ETIQUETAS_REVISTA WHERE revista = ?";

    private static int getCantEtcDeRevista(int revistaId, String query) throws InvalidInputType, SQLException {
        int out = -1;

        PreparedStatement stmt = Conexion.getPrepareStatement(query);

        stmt.setInt(1, revistaId);

        ResultSet res = stmt.executeQuery();

        if (res.next())
            out = res.getInt(1);

        stmt.close();
        Conexion.closeSession();
        return out;
    }

    private static String[] getEtiquetasDeRevista(int revistaId) throws SQLException {
        List<String> tags = new ArrayList<>();

        PreparedStatement stmt = Conexion.getPrepareStatement(ETIQUETAS_REVISTA);

        stmt.setInt(1, revistaId);

        ResultSet res = stmt.executeQuery();

        while (res.next())
            tags.add(res.getString(1));

        stmt.close();
        Conexion.closeSession();
        return tags.toArray(new String[0]);
    }

    public static String getRevistasSuscriptas(String username)
            throws SQLException, InvalidInputType, TooManyArgumentsException {

        List<Revista> suscripciones = new ArrayList<>();
        UserFactory.createUser(username, null, UserType.SUSCRIPTOR);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(SUSCRIPCIONES);

        stmt.setString(1, username);

        ResultSet res = stmt.executeQuery();

        while (res.next()) {
            int id = res.getInt(1);
            String editor = res.getString(2);
            Date fechaPublicacion = res.getDate(3);
            String nombre = res.getString(4);
            String categoria = res.getString(5);

            suscripciones.add(Revista.createRevista(id, editor, fechaPublicacion, nombre, categoria));
        }

        stmt.close();
        Conexion.closeSession();

        return GSON_FOR_DATE.toJson(suscripciones);
    }

    public static String getRevistaFromSuscripcion(String json) throws InvalidInputType, SQLException {
        Revista revista = null;
        Suscripcion suscripcion = toSuscripcionValidada(json);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(REVISTA_SUSCRITO);

        stmt.setString(1, suscripcion.getSuscriptor());
        stmt.setInt(2, suscripcion.getRevista());

        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            int id = res.getInt(1);
            String editor = res.getString(2);
            Date fechaPublicacion = res.getDate(3);
            String nombre = res.getString(4);
            String categoria = res.getString(5);
            int costoMes = res.getInt(6);
            String descripcion = res.getString(7);
            boolean comentariosActivos = res.getBoolean(8);
            comentariosActivos = (res.wasNull()) ? true : comentariosActivos;
            boolean meGustasActivos = res.getBoolean(9);
            meGustasActivos = (res.wasNull()) ? true : meGustasActivos;
            boolean suscripcionesActivas = res.getBoolean(10);
            suscripcionesActivas = (res.wasNull()) ? true : suscripcionesActivas;

            revista = Revista.createRevista(id, editor, fechaPublicacion, nombre, categoria, costoMes, descripcion,
                    comentariosActivos, meGustasActivos, suscripcionesActivas);

            revista.setMeGustas(getCantEtcDeRevista(revista.getId(), ME_GUSTAS_REVISTA));
            revista.setNumeros(getCantEtcDeRevista(revista.getId(), CANT_NUMEROS_REVISTA));
            revista.setEtiquetas(getEtiquetasDeRevista(revista.getId()));
        }

        stmt.close();
        Conexion.closeSession();

        return (revista != null) ? GSON_FOR_DATE.toJson(revista) : null;
    }

    public static String getRevistaFromSinSuscripcion(String json) throws InvalidInputType, SQLException {
        Revista revista = null;
        Suscripcion suscripcion = toSuscripcionValidada(json);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(REVISTA_NO_SUSCRITO);

        stmt.setInt(1, suscripcion.getRevista());

        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            int id = res.getInt(1);
            String editor = res.getString(2);
            Date fechaPublicacion = res.getDate(3);
            String nombre = res.getString(4);
            String categoria = res.getString(5);
            String descripcion = res.getString(6);
            boolean suscripcionesActivas = res.getBoolean(7);
            suscripcionesActivas = (res.wasNull()) ? true : suscripcionesActivas;

            revista = Revista.createRevista(id, editor, fechaPublicacion, nombre, categoria);

            revista.setDescripcion(descripcion);
            revista.setSuscripcionesActivas(suscripcionesActivas);
            revista.setMeGustas(getCantEtcDeRevista(revista.getId(), ME_GUSTAS_REVISTA));
            revista.setEtiquetas(getEtiquetasDeRevista(revista.getId()));
        }

        stmt.close();
        Conexion.closeSession();

        return (revista != null) ? GSON_FOR_DATE.toJson(revista) : null;
    }

}
