package edu.publishPDF.xhr.revistas;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.revistas.RevistaSetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputReader;

@WebServlet(name = "CreateRevista", urlPatterns = {"/revista"})
public class CreateRevista extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (InputReader reader = new InputReader(request.getReader())) {
            String revistaJson = reader.readInput();
            boolean publicada = RevistaSetter.publishRevista(revistaJson);
            response.getWriter().print(publicada);
        } catch (SQLException e) {
            response.sendError(500, "No logramos ingresar su publicacion.");
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        }
    }

}
