package edu.publishPDF.xhr.retroalimentacion;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.retroalimentacion.ComentarioSetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputReader;

@WebServlet(name = "CreateComentario", urlPatterns = {"/comentarios/"})
public class CreateComentario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (InputReader reader = new InputReader(request.getReader())) {
            String comentarioJson = reader.readInput();
            boolean allRigth = ComentarioSetter.comentar(comentarioJson);
            
            if (allRigth)
                response.setStatus(201);
            else
                response.sendError(400, "No se pudo crear o actualizar el comentario.");
        } catch (IOException | SQLException e) {
            response.sendError(500, "No se pudo crear o actualizar el comentario.");
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        }
    }

}
