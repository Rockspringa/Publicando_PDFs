package edu.publishPDF.database.accesors.anuncio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.anuncio.Anuncio;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;
import edu.publishPDF.model.users.UserType;
import edu.publishPDF.model.users.types.UserFactory;

public class AnuncioGetter extends AccesorTools {

    private static final String ANUNCIOS_POR_TAG = "SELECT EA.anuncio, texto, administrador, imagen, video "
            + "FROM ETIQUETAS_SUSCRIPTOR ES JOIN ETIQUETAS_ANUNCIO EA ON ES.etiqueta = EA.etiqueta JOIN ANUNCIO"
            + " ON EA.anuncio = anuncio_id WHERE suscriptor = ? AND activo = 1 AND fecha_publicacion < ?";

    public static String findAllAds(String username) throws InvalidInputType, TooManyArgumentsException,
            SQLException {
        List<Anuncio> anuncios = new ArrayList<>();
        UserFactory.createUser(username, null, UserType.SUSCRIPTOR);

        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(ANUNCIOS_POR_TAG);
        stmt.setString(1, username);

        ResultSet res = stmt.executeQuery();

        while (res.next()) {
            int id = res.getInt(1);
            String texto = res.getString(2);

            Anuncio anuncio = Anuncio.createAnuncio(id, true, texto);
            
            anuncio.setAdministrador(res.getString(3));
            anuncio.setImagen(res.getString(4));
            anuncio.setVideo(res.getString(5));

            anuncios.add(anuncio);
        }

        stmt.close();
        Conexion.closeSession();

        return GSON_FOR_DATE.toJson(anuncios);
    }
}
