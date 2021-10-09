package edu.publishPDF.xhr.users;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.user.UserGetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;
import edu.publishPDF.model.users.UserType;

@WebServlet(name = "ReadUsuario", urlPatterns = { "/usuario/read" })
public class ReadUsuario extends HttpServlet {

    /**
     * Lee los datos de un usuario sabiendo su tipo y su nombre de usuario. Puede
     * utilizarse para mostrar los datos en una visualizacion de perfil.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("user");
        String tipo = request.getParameter("type");
        String output = null;

        try {
            UserType type = UserType.valueOf(tipo);
            output = UserGetter.getUserData(username, type);

            if (output == null || output.equals(""))
                response.sendError(404, "No se encontro el usuario, compuebe los datos.");
            else
                response.getWriter().print(output);

        } catch (SQLException | IOException e) {
            response.sendError(500, e.getMessage());
        } catch (InvalidInputType | TooManyArgumentsException e) {
            response.sendError(400, e.getMessage());
        } catch (NullPointerException | IllegalArgumentException e) {
            response.sendError(400, "Hubo un error al solicitar la lectura del usuario.");
        }
    }

    /**
     * Lee un usuario usando su nombre de usuario y su contraseña, se usa para
     * logearse. Se regresa el usuario con sus datos para saber a que tipo
     * pertenece.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String output = null;

        try {
            output = UserGetter.getUserData(username, password);

            if (output == null || output.equals(""))
                response.sendError(404, "No se encontro el usuario, compuebe los datos.");
            else {
                response.getWriter().print(output);
            }
        } catch (SQLException | IOException e) {
            response.sendError(500, e.getMessage());
        } catch (InvalidInputType | TooManyArgumentsException e) {
            response.sendError(400, e.getMessage());
        } catch (NullPointerException | IllegalArgumentException e) {
            response.sendError(400, "Hubo un error al solicitar la lectura del usuario.");
        }
    }

}
