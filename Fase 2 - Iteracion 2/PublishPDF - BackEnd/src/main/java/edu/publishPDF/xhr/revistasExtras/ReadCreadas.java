package edu.publishPDF.xhr.revistasExtras;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.revistas.RevistaGetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;

@WebServlet(name = "ReadCreadas", urlPatterns = {"/revista/creadas"})
public class ReadCreadas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("user");

        try {
            String revistasJson = RevistaGetter.getRevistasCreadas(username);
            response.getWriter().print(revistasJson);
        } catch (SQLException | IOException e) {
            response.sendError(500, "Ocurrio un error inesperado.");
        } catch (InvalidInputType | TooManyArgumentsException e) {
            response.sendError(400, e.getMessage());
        }
    }

}
