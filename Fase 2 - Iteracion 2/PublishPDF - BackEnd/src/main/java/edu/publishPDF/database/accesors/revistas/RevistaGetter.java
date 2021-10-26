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
import edu.publishPDF.model.revistas.NumeroRevista;
import edu.publishPDF.model.revistas.Revista;
import edu.publishPDF.model.users.UserType;
import edu.publishPDF.model.users.types.UserFactory;

public class RevistaGetter extends AccesorTools {

    private static final String BUSCAR = "SELECT revista_id, editor, fecha_publicada, nombre, categoria"
            + " FROM REVISTA LEFT JOIN ETIQUETAS_REVISTA ON revista = revista_id WHERE etiqueta"
            + " LIKE CONCAT('%', ?, '%') OR categoria LIKE CONCAT('%', ?, '%') GROUP BY revista";

    private static final String REVISTA = "SELECT editor, fecha_publicada, nombre, categoria,"
            + " coste_mes, suscripciones_activas FROM REVISTA WHERE revista_id = ?";

    private static final String REVISTAS_CREADAS = "SELECT revista_id, fecha_publicada, nombre, "
            + "categoria FROM REVISTA WHERE editor = ?";

    private static final String NEW_NUMERO = "SELECT COUNT(*) FROM NUMERO_REVISTA WHERE revista = ?";

    private static final String REVISTA_RECIENTE = "SELECT COUNT(revista_id) FROM REVISTA";

    private static final String GET_ARCHIVO = "SELECT archivo FROM NUMERO_REVISTA WHERE numero_id = ?"
            + " AND revista = ?";

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

    public static String getRevistasCreadas(String username)
            throws SQLException, InvalidInputType, TooManyArgumentsException {

        List<Revista> revistas = new ArrayList<>();
        UserFactory.createUser(username, null, UserType.EDITOR);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(REVISTAS_CREADAS);

        stmt.setString(1, username);

        ResultSet res = stmt.executeQuery();

        while (res.next()) {
            int id = res.getInt(1);
            Date fechaPublicacion = res.getDate(2);
            String nombre = res.getString(3);
            String categoria = res.getString(4);

            revistas.add(Revista.createRevista(id, username, fechaPublicacion, nombre, categoria));
        }

        stmt.close();
        Conexion.closeSession();

        return GSON_FOR_DATE.toJson(revistas);
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

    public static int getNewNumero(int revista) throws InvalidInputType, SQLException {
        if (revista < 1)
            throw new InvalidInputType();

        int numeroActual = 0;
        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(NEW_NUMERO);

        stmt.setInt(1, revista);

        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            numeroActual = res.getInt(1);
        }

        stmt.close();
        Conexion.closeSession();

        return numeroActual + 1;
    }

    public static int getRevistaReciente() throws InvalidInputType, SQLException {
        int numeroActual = 0;
        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(REVISTA_RECIENTE);

        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            numeroActual = res.getInt(1);
        }

        stmt.close();
        Conexion.closeSession();

        return numeroActual;
    }

    public static String getRelativePathNumero(int number, int revista) throws InvalidInputType, SQLException {
        String archivo = null;
        NumeroRevista numero = NumeroRevista.createNumeroRevista(number, revista, null);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(GET_ARCHIVO);

        stmt.setInt(1, numero.getId());
        stmt.setInt(2, numero.getRevista());

        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            archivo = res.getString(1);
        }

        stmt.close();
        Conexion.closeSession();

        return archivo;
    }

}
