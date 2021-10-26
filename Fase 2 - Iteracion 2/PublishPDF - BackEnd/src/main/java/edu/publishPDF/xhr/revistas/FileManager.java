package edu.publishPDF.xhr.revistas;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import edu.publishPDF.database.accesors.revistas.RevistaGetter;
import edu.publishPDF.database.accesors.revistas.RevistaSetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.filesTools.FilePasser;

@MultipartConfig()
@WebServlet(name = "FileManager", urlPatterns = {"/revista/archivo/*"})
public class FileManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String root = getServletContext().getRealPath("/");
        int revista = Integer.parseInt(req.getParameter("revista"));
        int numero = Integer.parseInt(req.getParameter("numero"));
        boolean descargar = Boolean.parseBoolean(req.getParameter("descargar"));

        try {
            String relativePath = RevistaGetter.getRelativePathNumero(numero, revista);
            String path = root + relativePath;
            File file = new File(path);
            
            resp.setContentType("application/pdf");
            if (descargar)
                resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());

            try (BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(file))) {
                int fileBits = fileStream.read();
    
                while (fileBits > -1) {
                    resp.getOutputStream().write(fileBits);
                    fileBits = fileStream.read();
                }
            }
        } catch (InvalidInputType | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            resp.sendError(400, "Hubo un problema con los datos que envio, revise sus datos.");
        } catch (SQLException | IOException e) {
            resp.sendError(500, "Es posible que no se haya podido entregar el archivo correctamente.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String root = getServletContext().getRealPath("/");
        Part partFile = request.getPart("file");
        String userData = request.getPathInfo();

        try {
            int revista = -1;
            if (userData.split("/").length > 1) {
                revista = Integer.parseInt(userData.split("/")[1]);
            } else {
                revista = RevistaGetter.getRevistaReciente();
                userData = userData + revista;
            }
            
            int numero = RevistaGetter.getNewNumero(revista);
            userData = userData + "/" + numero;

            FilePasser creator = new FilePasser(root, partFile, userData);
            if (userData.split("/").length == 3 && creator.getType().equals("PDF")) {
                if (creator.passFile()) {
                    RevistaSetter.publishNumero(numero, revista, creator.getPath());
                    response.setStatus(200);
                }
            } else 
                response.sendError(400, "El archivo enviado no es de tipo pdf.");
        } catch (InvalidInputType | IllegalStateException | NullPointerException e) {
            response.sendError(400, "Compruebe los datos enviados.");
        } catch (IOException | SQLException e) {
            response.sendError(500, "No se pudo guardar el archivo.");
        }
    }

}
