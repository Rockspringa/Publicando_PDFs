package edu.publishPDF.xhr.revistas;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.revistas.SuscritoGetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;
import edu.publishPDF.tools.InputReader;

@WebServlet(name = "ReadSuscripciones", urlPatterns = {"/suscripciones"})
public class ReadSuscripciones extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("user");

        try {
            String suscripcionesJson = SuscritoGetter.getRevistasSuscriptas(username);
            response.getWriter().print(suscripcionesJson);
        } catch (SQLException | TooManyArgumentsException e) {
            response.sendError(500, "No se pudo encontrar las suscripciones del usuario.");
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        } catch (NullPointerException e) {
            response.sendError(400, "No se encontro nada, esta nulo.");
        } catch (IOException e) {
            response.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (InputReader reader = new InputReader(request.getReader())) {
            String suscripcionJson = reader.readInput();

            String revistaJson = SuscritoGetter.getRevistaFromSuscripcion(suscripcionJson);

            if (revistaJson == null)
                revistaJson = SuscritoGetter.getRevistaFromSinSuscripcion(suscripcionJson);
            
            response.getWriter().print(revistaJson);
        } catch (SQLException e) {
            response.sendError(500, "No se pudo encontrar las suscripciones del usuario.");
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        } catch (NullPointerException e) {
            response.sendError(400, "No se encontro nada, esta nulo.");
        }
    }

}
