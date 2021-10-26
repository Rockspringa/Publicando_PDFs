package edu.publishPDF.xhr.revistasExtras;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.user.ExtrasGetter;

@WebServlet(name = "ReadTagsRevista", urlPatterns = {"/revista/tags"})
public class ReadTagsRevista extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String tagsJson = ExtrasGetter.getAllTagsPublic();
            response.getWriter().print(tagsJson);
        } catch (NumberFormatException e) {
            response.sendError(400, "Hubo un error con los datos. Verifique los datos enviados.");
        } catch (SQLException e) {
            response.sendError(500, "No se pudieron recuperar las etiquetas.");
        }
    }

}
