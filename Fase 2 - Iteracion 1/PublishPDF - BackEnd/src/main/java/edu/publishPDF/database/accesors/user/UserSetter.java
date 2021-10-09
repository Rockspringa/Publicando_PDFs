package edu.publishPDF.database.accesors.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.UserAccesor;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;
import edu.publishPDF.model.users.UserType;
import edu.publishPDF.model.users.types.UserFactory;

public class UserSetter extends UserAccesor {
    
    private static final String UPD_USER = "UPDATE USUARIO SET %s "
            + "WHERE nombre_usuario = ? AND contraseña LIKE BINARY ?;";

    private static final String UPD_USER_FOTO = "UPDATE USUARIO SET foto = ? WHERE nombre_usuario = ?;";

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

        StringBuilder sb = new StringBuilder();
        for (String key : args.keySet())
            if (!key.equals("nombre_usuario") && !key.equals("contraseña"))
                sb.append(key + " = ?, ");

        sb.replace(sb.length() - 2, sb.length(), "");

        String query = String.format(UPD_USER, UserAccesor.toCsv(false, sb.toString()));

        PreparedStatement stmt = Conexion.getPrepareStatement(query);

        int j = 1;
        for (String key : args.keySet())
            if (!key.equals("nombre_usuario") && !key.equals("contraseña"))
                stmt.setString(j++, args.get(key));

        stmt.setString(j++, args.get("nombre_usuario"));
        stmt.setString(j++, args.get("contraseña"));

        int res = stmt.executeUpdate();

        Conexion.closeSession();

        return res > 0;
    }

    public static boolean updatePhoto(String username, String fotoname)
            throws InvalidInputType, SQLException, TooManyArgumentsException {
        UserFactory.createUser(username, null, UserType.SUSCRIPTOR);
        Conexion.createSession();

        PreparedStatement stmt = Conexion.getPrepareStatement(UPD_USER_FOTO);

        stmt.setString(1, fotoname);
        stmt.setString(2, username);

        int res = stmt.executeUpdate();

        Conexion.closeSession();

        return res > 0;
    }

}
