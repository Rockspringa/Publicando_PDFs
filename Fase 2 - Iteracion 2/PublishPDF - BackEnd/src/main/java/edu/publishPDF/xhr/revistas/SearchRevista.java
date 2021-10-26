package edu.publishPDF.xhr.revistas;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.revistas.RevistaGetter;
import edu.publishPDF.model.errores.InvalidInputType;

@WebServlet(name = "SearchRevista", urlPatterns = {"/revista/buscar"})
public class SearchRevista extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String criterio = request.getParameter("criterio");
        
        try {
            String revistasArrayJson = RevistaGetter.searchRevista(criterio);
            response.getWriter().print(revistasArrayJson);
        } catch (SQLException e) {
            response.sendError(500, "No se pudo encontrar ninguna revista con ese criterio.");
        } catch (InvalidInputType | IOException e) {
            response.sendError(400, e.getMessage());
        } catch (NullPointerException e) {
            response.sendError(400, "No se encontro nada, esta nulo.");
        }
    }

}
