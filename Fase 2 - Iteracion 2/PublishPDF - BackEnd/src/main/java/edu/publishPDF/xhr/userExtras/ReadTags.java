package edu.publishPDF.xhr.userExtras;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.user.ExtrasGetter;

@WebServlet(name = "ReadTags", urlPatterns = {"/usuario/tags"})
public class ReadTags extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("user");

        try {
            String allAndUserTagsJson = ExtrasGetter.getAllAndUserTags(username);

            if (allAndUserTagsJson != null)
                response.getWriter().print(allAndUserTagsJson);
            else
                response.sendError(500, "No se pudo obtener nada de los tags");
        } catch (SQLException e) {
            response.sendError(500, "Se revolvieron todos los tags, intentelo mas tarde.");
        }
    }

}
