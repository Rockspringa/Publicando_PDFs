package edu.publishPDF.xhr.revistasExtras;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.revistas.CategoriaGetter;

@WebServlet(name = "ReadCategorias", urlPatterns = {"/revista/categorias"})
public class ReadCategorias extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String categorias = CategoriaGetter.findAllCategories();
            
            response.getWriter().print(categorias);
        } catch (SQLException e) {
            response.sendError(500, "Hubo un problema al recuperar las categorias");
        }
    }

}
