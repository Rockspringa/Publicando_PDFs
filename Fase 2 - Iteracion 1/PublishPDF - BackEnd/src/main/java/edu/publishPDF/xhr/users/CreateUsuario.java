package edu.publishPDF.xhr.users;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.Conexion;
import edu.publishPDF.database.accesors.user.UserCreator;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputReader;

@WebServlet(name = "CreateUsuario", urlPatterns = { "/usuario" })
public class CreateUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (InputReader reader = new InputReader(request.getReader())) {
            String json = reader.readInput();
            boolean output = UserCreator.createUser(json);

            if (output) {
                response.setStatus(201);
                response.getWriter().print(json);
            }
            else
                response.sendError(404, "El nombre de usuario ya existe.");

        } catch (SQLException | IOException e) {
            if (e instanceof SQLException) {
                try {
                    Conexion.finishTransaction(false);
                } catch (SQLException e1) {
                }
            }
            response.sendError(500, e.getMessage());
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        } catch (NullPointerException | IllegalArgumentException e) {
            response.sendError(400, "Hubo un error al solicitar la creacion del usuario.");
        }
    }

}
