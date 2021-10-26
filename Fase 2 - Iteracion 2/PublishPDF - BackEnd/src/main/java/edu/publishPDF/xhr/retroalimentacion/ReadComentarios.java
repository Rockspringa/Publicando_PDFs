package edu.publishPDF.xhr.retroalimentacion;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.retroalimentacion.ComentariosGetter;
import edu.publishPDF.model.errores.InvalidInputType;

@WebServlet(name = "ReadComentarios", urlPatterns = {"/comentarios"})
public class ReadComentarios extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int revistaId = Integer.parseInt(request.getParameter("revista"));
            String comentariosJson = ComentariosGetter.getComentariosDeRevista(revistaId);

            response.getWriter().print(comentariosJson);
        } catch (NullPointerException | NumberFormatException e) {
            response.sendError(400, "No se pudieron recuperar los datos enviados.");
        } catch (SQLException | IOException e) {
            response.sendError(500, "Hubo un error al leer los comentarios.\n" + e.getMessage());
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        }
    }

}
