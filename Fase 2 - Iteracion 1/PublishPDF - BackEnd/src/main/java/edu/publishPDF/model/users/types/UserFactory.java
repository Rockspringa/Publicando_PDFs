package edu.publishPDF.model.users.types;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;
import edu.publishPDF.model.users.User;
import edu.publishPDF.model.users.UserType;
import edu.publishPDF.model.users.errors.UnknownUserException;
import edu.publishPDF.tools.InputValidator;

public class UserFactory {

    private static final Gson GSON = new Gson();

    private static void verifyStrings(String[] args1, String... args2) throws InvalidInputType {
        for (String arg : args1)
            if (arg != null && !InputValidator.isValidText(arg))
                throw new InvalidInputType();

        for (String arg : args2)
            if (arg != null && !InputValidator.isValidText(arg))
                throw new InvalidInputType();
    }

    /**
     * Devuelve los parametros del objeto en cuestion.
     * 
     * @param user el objeto al que se le extraeran los parametros.
     * @return una colleccion key <=> value;
     */
    public static Map<String, String> getStringAttributes(User user) {
        Field[] fields = User.class.getDeclaredFields();
        Map<String, String> args = new HashMap<>();

        for (Field field : fields) {
            if (field.getType() == String.class) {
                try {
                    args.put((String) field.getName(), (String) field.get(user));
                } catch (IllegalAccessException e) {
                }
            }
        }
        return args;
    }

    /**
     * Llena los parametros no obligatorios de los usuarios. Los atributos deben
     * tener el orden especifico y se pueden reemplazar por null para llegar al
     * atributo que desea modificar. El orden seria:
     * <ol>
     * <li>Nombre</li>
     * <li>Descripcion</li>
     * <li>Gustos</li>
     * <li>Hobbies</li>
     * <li>Path de la foto</li>
     * </ol>
     * 
     * @param user es el objeto al cual se le llenaran los atributos.
     * @param args son los atributos
     * @throws TooManyArgumentsException si tiene mas de los cinco atributos
     *                                   especificados.
     */
    public static void fillUserAttributes(User user, String... args) throws TooManyArgumentsException {
        if (args.length > 5)
            throw new TooManyArgumentsException(5);

        try {
            user.setNombre(args[0]);
            user.setDescripcion(args[1]);
            user.setGustos(args[2]);
            user.setHobbies(args[3]);
            user.setFotoPath(args[4]);
        } catch (IndexOutOfBoundsException e) {
            // solo para que no lance error hacia atras y saltarse verificacion de length
        }
    }

    /**
     * Crea un usuario con sus parametros, hay dos principales, el nombre de usuario
     * y la contraseña, los parametros secundarios poseen un orden especifico. El
     * orden seria:
     * <ol>
     * <li>Nombre</li>
     * <li>Descripcion</li>
     * <li>Gustos</li>
     * <li>Hobbies</li>
     * <li>Path de la foto</li>
     * </ol>
     * 
     * @param username es el nombre de usuario del usuario.
     * @param password es la contraseña para seguridad de diferenciar al usuario.
     * @param type     es el tipo de usuario que se creara.
     * @param args     son los parametros extras, pueden ser null para llegar al
     *                 atributo indicado antes.
     * @return un objeto usuario del tipo especificado y con sus atributos.
     * @throws InvalidInputType          cuando la entrada no es texto valido, sin
     *                                   simbolos o saltos de linea.
     * @throws TooManyArgumentsException si args tiene más de 5 argumentos.
     */
    public static User createUser(String username, String password, UserType type, String... args)
            throws InvalidInputType, TooManyArgumentsException {
        User user = null;

        verifyStrings(args, username, password);

        switch (type) {
            case SUSCRIPTOR:
                user = new Suscriptor(username, password);
                break;

            case EDITOR:
                user = new Editor(username, password);
                break;

            case ADMINISTRADOR:
                user = new Administrador(username, password);
                break;

            default:
                throw new UnknownUserException();
        }
        UserFactory.fillUserAttributes(user, args);

        return user;
    }

    /**
     * Crea un usuario basandose en la entrada de un string que represente un JSON.
     * 
     * @param json es el string de donde se sacaran los atributos.
     * @return un usuario, puede ser de tipo Suscriptor, Editos, Administrador.
     * @throws InvalidInputType Si uno de los atributos es invalido.
     */
    public static User createUserFromJson(String json) throws InvalidInputType {
        User user = null;

        if (json.contains("\"_tags\":")) {
            user = GSON.fromJson(json, Suscriptor.class);
        } else if (json.contains("\"_revistas\":")) {
            user = GSON.fromJson(json, Editor.class);
        } else if (json.contains("\"_anuncios\":")) {
            user = GSON.fromJson(json, Administrador.class);
        }
        verifyStrings(getStringAttributes(user).values().toArray(new String[0]));

        return user;
    }

}
