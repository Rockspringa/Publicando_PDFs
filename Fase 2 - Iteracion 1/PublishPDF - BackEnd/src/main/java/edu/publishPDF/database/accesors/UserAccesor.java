package edu.publishPDF.database.accesors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;
import edu.publishPDF.model.users.User;
import edu.publishPDF.model.users.UserType;
import edu.publishPDF.model.users.types.UserFactory;

public class UserAccesor {

    private static final Gson GSON = new Gson();

    private static final String[] EXTRA_PARAMS = { "nombre", "descripcion", "gustos", "hobbies", "foto" };

    private static final String CREATE_SEA = "INSERT INTO %s VALUES ?;";
    private static final String CREATE_USER = "INSERT INTO USUARIO (nombre_usuario, "
            + "contraseña, nombre%s) VALUES (%s);";

    private static final String GET_USER = "SELECT nombre FROM "
            + "USUARIO WHERE nombre_usuario = ? AND contraseña LIKE BINARY ?;";

    private static final String GET_USER_TYPE = "SELECT nombre, descripcion, gustos, hobbies, foto FROM "
            + "%s JOIN USUARIO ON usuario = nombre_usuario WHERE usuario = ?;";

    private static final String UPD_USER = "UPDATE USUARIO SET FROM %s "
            + "WHERE nombre_usuario = ? AND contraseña LIKE BINARY ?;";

    private static String toCsv(boolean firstComma, String... args) {
        StringBuilder sb = new StringBuilder((firstComma) ? ", " : "");

        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                sb.append(args[i]);

                if (i != args.length - 1) {
                    sb.append(", ");
                }
            }
        }

        return (args.length != 0) ? sb.toString() : "";
    }

    public static String[] cookieJsonToData(String json) {
        JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();

        String[] out = new String[3];
        out[0] = jsonObj.get("username").getAsString();
        out[1] = jsonObj.get("nombre").getAsString();
        out[2] = jsonObj.get("type").getAsString();

        return out;
    }

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
            for (UserType type : UserType.values()) {
                String user = getUserData(username, type);
                if (user != null)
                    return user;
            }
            return null;
        } else {
            Conexion.closeSession();
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

        Conexion.closeSession();

        return out;
    }

    /**
     * Crea un usuario en la base de datos con los datos
     * 
     * @param json es el objeto que contiene toda la informacion del usuario.
     * @return <code>true</code> si se logro crear el usuario, <code>false</code> de
     *         lo contrario.
     * @throws SQLException
     * @throws InvalidInputType si los datos proporcionados no son validos.
     */
    public static boolean createUser(String json) throws SQLException, InvalidInputType {
        User user = UserFactory.createUserFromJson(json);
        Map<String, String> args = UserFactory.getStringAttributes(user);
        boolean out = false;

        String[] keys = args.keySet().toArray(new String[0]);
        String[] campos = new String[args.size()];
        for (int i = 0; i < args.size(); i++)
            if (args.get(keys[i]) != null)
                campos[i] = EXTRA_PARAMS[i];

        Conexion.createSession();
        Conexion.setAutoCommit(false);

        String query = String.format(CREATE_USER, UserAccesor.toCsv(true, campos));
        query = String.format(query, UserAccesor.toCsv(false, args.values().toArray(new String[0])));

        PreparedStatement stmt = Conexion.getPrepareStatement(query);

        stmt.setString(1, args.get("username"));
        stmt.setString(2, args.get("password"));

        int j = 1;
        for (String key : args.keySet())
            if (!key.equals("username") && !key.equals("password"))
                stmt.setString(j++, args.get(key));

        int res = stmt.executeUpdate();

        if (res > 0) {
            stmt.close();
            stmt = Conexion
                    .getPrepareStatement(String.format(CREATE_SEA, user.getClass().getSimpleName().toUpperCase()));
            stmt.setString(1, args.get("username"));
            res = stmt.executeUpdate();

            if (res > 0) {
                Conexion.finishTransaction(true);
                out = true;
            } else
                Conexion.finishTransaction(false);
        } else
            Conexion.finishTransaction(false);

        Conexion.closeSession();

        return out;
    }

    /**
     * Actualiza el usuario que contenga el mismo nombre de usuario que el nombre de
     * usuario contenido por el json.
     * 
     * @param json es el usuario, su informacion actualizada.
     * @return <code>true</code> si se actualizo alguna fila, <code>false</code> de
     *         lo contrario.
     * @throws InvalidInputType si un input no es valido.
     * @throws SQLException
     */
    public static boolean updateUser(String json) throws InvalidInputType, SQLException {
        Map<String, String> args = UserFactory.getStringAttributes(UserFactory.createUserFromJson(json));
        Conexion.createSession();

        String query = String.format(UPD_USER, UserAccesor.toCsv(true, args.values().toArray(new String[0])));

        PreparedStatement stmt = Conexion.getPrepareStatement(query);

        int j = 1;
        for (String key : args.keySet())
            if (!key.equals("username") && !key.equals("password"))
                stmt.setString(j++, args.get(key));

        stmt.setString(1, args.get("username"));
        stmt.setString(2, args.get("password"));

        int res = stmt.executeUpdate();

        Conexion.closeSession();

        return res > 0;
    }
}
