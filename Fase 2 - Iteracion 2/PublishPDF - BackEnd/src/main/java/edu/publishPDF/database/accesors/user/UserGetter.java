package edu.publishPDF.database.accesors.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;
import edu.publishPDF.model.users.User;
import edu.publishPDF.model.users.UserType;
import edu.publishPDF.model.users.types.UserFactory;

public class UserGetter extends AccesorTools {

    private static final String GET_USER = "SELECT nombre FROM "
            + "USUARIO WHERE nombre_usuario = ? AND contraseña LIKE BINARY ?;";

    private static final String GET_USER_TYPE = "SELECT nombre, descripcion, gustos, hobbies, foto FROM "
            + "%s JOIN USUARIO ON usuario = nombre_usuario WHERE usuario = ?;";

    private static final String READ_FOTO = "SELECT foto FROM USUARIO WHERE nombre_usuario = ?;";

    /**
     * Trae de la base de datos la informacion de un usuario, sin importar su tipo.
     * 
     * @param username es el nombre de usuario del usuario.
     * @param password es la llave de acceso del usuario.
     * @return una cadena de texto con un JSON que representa al usuario.
     * @throws SQLException
     * @throws InvalidInputType          si la entrada de texto no es valida.
     * @throws TooManyArgumentsException excede los argumentos extra, (no deberia
     *                                   lanzarse nunca)
     */
    public static String getUserData(String username, String password)
            throws SQLException, InvalidInputType, TooManyArgumentsException {
        // Solo corrobora los campos, por eso no se guarda el usuario
        UserFactory.createUser(username, password, UserType.SUSCRIPTOR);
        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(GET_USER);

        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            stmt.close();
            for (UserType type : UserType.values()) {
                String user = getUserData(username, type);
                if (user != null) {
                    Conexion.closeSession();
                    return user;
                }
            }
            Conexion.closeSession();
            return null;
        } else {
            stmt.close();
            return null;
        }
    }

    /**
     * Trae de la base de datos la informacion de un usuario, importando su tipo.
     * 
     * @param username es el nombre de usuario del usuario.
     * @param type     es el tipo del usuario, solo para comprobar datos.
     * @return una cadena de texto con un JSON que representa al usuario.
     * @throws SQLException
     * @throws InvalidInputType          si la entrada de texto no es valida.
     * @throws TooManyArgumentsException excede los argumentos extra, (no deberia
     *                                   lanzarse nunca)
     */
    public static String getUserData(String username, UserType type)
            throws SQLException, InvalidInputType, TooManyArgumentsException {
        User user = UserFactory.createUser(username, null, type);
        String out = null;
        Conexion.createSession();
        String[] args = new String[5];

        PreparedStatement stmt = Conexion.getPrepareStatement(String.format(GET_USER_TYPE, type));

        stmt.setString(1, username);

        ResultSet res = stmt.executeQuery();

        if (res.next())
            for (int i = 0; i < 5; i++)
                args[i] = res.getString(i + 1);
        else
            return null;

        UserFactory.fillUserAttributes(user, args);
        out = GSON.toJson(user);

        stmt.close();
        Conexion.closeSession();

        return out;
    }

    public static String getPhoto(String username) throws InvalidInputType, SQLException, TooManyArgumentsException {
        UserFactory.createUser(username, null, UserType.SUSCRIPTOR);
        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(READ_FOTO);

        stmt.setString(1, username);

        ResultSet res = stmt.executeQuery();
        if (res.next())
            return res.getString(1);

        stmt.close();
        Conexion.closeSession();

        return null;
    }

}
