package edu.publishPDF.database.accesors.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;

public class ExtrasSetter extends AccesorTools {
    
    private static final String DEL_USER_TAGS = "DELETE FROM ETIQUETAS_SUSCRIPTOR WHERE suscriptor = ?";

    private static final String UPD_USER_TAGS = "INSERT IGNORE INTO ETIQUETAS_SUSCRIPTOR SET suscriptor = ?,"
            + " etiqueta = ?";

    private static final String DEL_REVISTA_TAGS = "DELETE FROM ETIQUETAS_REVISTA WHERE revista = ?";

    private static final String UPD_REVISTA_TAGS = "INSERT IGNORE INTO ETIQUETAS_REVISTA SET revista = ?,"
            + " etiqueta = ?";

    public static final boolean updateUserTags(String username, String[] tags) throws SQLException {
        Conexion.createSession();
        Conexion.setAutoCommit(false);

        PreparedStatement stmt = Conexion.getPrepareStatement(DEL_USER_TAGS);
        stmt.setString(1, username);
        int rows = stmt.executeUpdate();

        stmt.close();

        for (String tag : tags) {
            stmt = Conexion.getPrepareStatement(UPD_USER_TAGS);
            stmt.setString(1, username);
            stmt.setString(2, tag);
            rows = stmt.executeUpdate();

            stmt.close();
            Conexion.closeSession();
            
            if (rows == 0) {
                Conexion.finishTransaction(false);
                Conexion.closeSession();
                return false;
            }
        }

        Conexion.finishTransaction(true);
        Conexion.closeSession();
        return true;
    }
    
    public static final boolean updateRevistaTags(int revista, String[] tags) throws SQLException {
        Conexion.createSession();
        Conexion.setAutoCommit(false);

        PreparedStatement stmt = Conexion.getPrepareStatement(DEL_REVISTA_TAGS);
        stmt.setInt(1, revista);
        int rows = stmt.executeUpdate();

        stmt.close();

        for (String tag : tags) {
            stmt = Conexion.getPrepareStatement(UPD_REVISTA_TAGS);
            stmt.setInt(1, revista);
            stmt.setString(2, tag);
            rows = stmt.executeUpdate();

            stmt.close();
            Conexion.closeSession();
            
            if (rows == 0) {
                Conexion.finishTransaction(false);
                Conexion.closeSession();
                return false;
            }
        }

        Conexion.finishTransaction(true);
        Conexion.closeSession();
        return true;
    }

}
