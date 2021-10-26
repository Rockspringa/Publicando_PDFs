package edu.publishPDF.xhr.anuncios;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.anuncio.AnuncioGetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;

@WebServlet(name = "ReadAnuncio", urlPatterns = {"/anuncio"})
public class ReadAnuncio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("user");
        try {
            String anunciosJson = AnuncioGetter.findAllAds(username);
            response.getWriter().print(anunciosJson);
        } catch (SQLException | IOException e) {
            response.sendError(500, "No se pudo encontar el anuncio adecuado.");
        } catch (InvalidInputType | TooManyArgumentsException e) {
            response.sendError(400, e.getMessage());
        }
    }

}
