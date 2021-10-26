package edu.publishPDF.xhr.retroalimentacion;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.retroalimentacion.LikesGetter;
import edu.publishPDF.database.accesors.retroalimentacion.LikesSetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputReader;

@WebServlet(name = "LikeRevista", urlPatterns = {"/suscripciones/like"})
public class LikeRevista extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String suscriptor = request.getParameter("user");
            int revistaId = Integer.parseInt(request.getParameter("revista"));

            LocalDate date = LikesGetter.dateLiked(revistaId, suscriptor);
            response.getWriter().print(date != null);
        } catch (NumberFormatException | InvalidInputType e) {
            response.sendError(400, "El parametro ingresado, no es valido.");
        } catch (SQLException e) {
            response.sendError(500, "Hubo un problema encontrando su like entre tantos que hay");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (InputReader reader = new InputReader(request.getReader())) {
            String suscripcionJson = reader.readInput();
            boolean liked = LikesSetter.darLike(suscripcionJson);
            response.getWriter().print(liked);
        } catch (SQLException e) {
            response.sendError(500, "Se confundieron los likes, no sabemos cual es el tuyo.");
        } catch (InvalidInputType e) {
            response.sendError(400, e.getMessage());
        }
    }

}
