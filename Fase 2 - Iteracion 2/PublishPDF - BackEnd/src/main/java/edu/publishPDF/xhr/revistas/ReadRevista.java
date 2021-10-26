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

@WebServlet(name = "ReadRevista", urlPatterns = {"/revista/"})
public class ReadRevista extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int revistaId = Integer.parseInt(request.getParameter("revista"));

        try {
            String revistaJson = RevistaGetter.getRevista(revistaId);
            if(revistaJson != null)
                response.getWriter().print(revistaJson);
            else
                response.sendError(500, "La revista se perdio en el estante.");
        } catch (SQLException e) {
            response.sendError(500, "No encontramos la revista, vuelva a intentarlo.");
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        }
    }

}
