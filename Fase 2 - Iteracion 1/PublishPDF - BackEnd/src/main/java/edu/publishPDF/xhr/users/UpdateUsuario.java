package edu.publishPDF.xhr.users;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.UserAccesor;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputReader;

@WebServlet(name = "UpdateUsuario", urlPatterns = { "/usuario/" })
public class UpdateUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (InputReader reader = new InputReader(request.getReader())) {
            boolean output = UserAccesor.updateUser(reader.readInput());

            if (output)
                response.setStatus(200);
            else
                response.sendError(404, "No se encontro el usuario, compuebe los datos.");

        } catch (SQLException | IOException e) {
            response.sendError(500, e.getMessage());
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        } catch (NullPointerException | IllegalArgumentException e) {
            response.sendError(400, "Hubo un error al solicitar la actualizacion del usuario.");
        }
    }

}
