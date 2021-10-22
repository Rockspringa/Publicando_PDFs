package edu.publishPDF.database.accesors.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.users.User;
import edu.publishPDF.model.users.types.UserFactory;

public class UserCreator extends AccesorTools {

    private static final String CREATE_SEA = "INSERT INTO %s VALUES (?);";

    private static final String CREATE_USER = "INSERT INTO USUARIO (%s) VALUES (%s);";

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
        String[] params = new String[args.size()];
        String[] campos = new String[args.size()];
        for (int i = 0; i < args.size(); i++) {
            if (args.get(keys[i]) != null && !args.get(keys[i]).equals("")) {
                campos[i] = keys[i];
                params[i] = "?";
            }
        }

        Conexion.createSession();
        Conexion.setAutoCommit(false);

        String query = String.format(CREATE_USER, AccesorTools.toCsv(false, campos),
                AccesorTools.toCsv(false, params));

        PreparedStatement stmt = Conexion.getPrepareStatement(query);

        int j = 1;
        for (String campo : campos)
            if (campo != null && !campo.equals(""))
                stmt.setString(j++, args.get(campo));

        int res = stmt.executeUpdate();

        if (res > 0) {
            stmt.close();
            stmt = Conexion
                    .getPrepareStatement(String.format(CREATE_SEA, user.getClass().getSimpleName().toUpperCase()));
            stmt.setString(1, args.get("nombre_usuario"));
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

}
