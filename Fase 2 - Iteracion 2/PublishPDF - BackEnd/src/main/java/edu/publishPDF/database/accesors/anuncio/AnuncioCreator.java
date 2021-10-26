package edu.publishPDF.database.accesors.anuncio;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.anuncio.Anuncio;
import edu.publishPDF.model.errores.InvalidInputType;

public class AnuncioCreator extends AccesorTools {
    
    private static final String CREAR = "INSERT INTO ANUNCIO (administrador, fecha_publicado, costo_dia, "
            + "activo, texto, imagen, video) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static Anuncio toAnuncioValidado(String json) throws InvalidInputType {
        Anuncio anuncio = GSON_FOR_DATE.fromJson(json, Anuncio.class);
        anuncio = Anuncio.createAnuncio(anuncio.getId(), anuncio.isActivo(), anuncio.getTexto());

        return anuncio;
    }

    public static boolean crearAnuncio(String json) throws InvalidInputType, SQLException {
        Anuncio anuncio = toAnuncioValidado(json);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(CREAR);
        
        stmt.setString(1, anuncio.getAdministrador());
        stmt.setDate(2, Date.valueOf(LocalDate.now()));
        stmt.setDouble(3, anuncio.getCostoDia());
        stmt.setBoolean(4, true);
        stmt.setString(5, anuncio.getTexto());
        stmt.setString(6, anuncio.getImagen());
        stmt.setString(7, anuncio.getVideo());

        int rows = stmt.executeUpdate();

        stmt.close();
        Conexion.closeSession();

        return rows > 0;
    }
}
