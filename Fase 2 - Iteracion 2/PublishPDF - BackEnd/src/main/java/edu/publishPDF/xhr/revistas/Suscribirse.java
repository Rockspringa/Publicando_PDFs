package edu.publishPDF.xhr.revistas;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.revistas.SuscripcionCreator;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputReader;

@WebServlet(name = "Suscribirse", urlPatterns = {"/suscripciones/"})
public class Suscribirse extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (InputReader reader = new InputReader(request.getReader())) {
            String suscripcionJson = reader.readInput();
            boolean cambios = SuscripcionCreator.suscribirseRevista(suscripcionJson);

            if (cambios) {
                response.setStatus(201);
                response.getWriter().print(cambios);
            } else
                response.sendError(500, "Perdimos la informacion necesario, vuelve a intentarlo.");
        } catch (SQLException e) {
            response.sendError(500, "No pudimos crear tu suscripcion en este momento.");
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        }
    }

}
