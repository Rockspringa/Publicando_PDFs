package edu.publishPDF.xhr.anuncios;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.anuncio.AnuncioCreator;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputReader;

@WebServlet(name = "CreateAnuncio", urlPatterns = {"/anuncio/"})
public class CreateAnuncio extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (InputReader reader = new InputReader(request.getReader())) {
            String anuncioJson = reader.readInput();
            boolean creado = AnuncioCreator.crearAnuncio(anuncioJson);
            response.getWriter().print(creado);
        } catch (SQLException e) {
            response.sendError(500, "No encontramos los planos de los anuncios.");
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        }
    }

}
