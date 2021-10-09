package edu.publishPDF.xhr.users;

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

import edu.publishPDF.database.accesors.user.UserGetter;
import edu.publishPDF.database.accesors.user.UserSetter;
import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.model.errores.TooManyArgumentsException;
import edu.publishPDF.tools.filesTools.FilePasser;

@MultipartConfig()
@WebServlet(name = "UploadFile", urlPatterns = { "/usuario/archivos/*" })
public class FileManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String root = getServletContext().getRealPath("/");
        String[] data = req.getPathInfo().split("/");

        try {
            String username = data[1];
            String relativePath = UserGetter.getPhoto(username);
            String path = root + relativePath;
            File file = new File(path);
            
            resp.setContentType("image/" + path.split("\\.")[1]);

            try (BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(file))) {
                int fileBits = fileStream.read();
    
                while (fileBits > -1) {
                    resp.getOutputStream().write(fileBits);
                    fileBits = fileStream.read();
                }
            }
        } catch (InvalidInputType | TooManyArgumentsException | ArrayIndexOutOfBoundsException e) {
            resp.sendError(400, e.getMessage());
        } catch (SQLException | IOException e) {
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String root = getServletContext().getRealPath("/");
        Part partFile = request.getPart("file");
        String userData = request.getPathInfo();

        try {
            FilePasser creator = new FilePasser(root, partFile, userData);
            if (creator.passFile()) {
                if (creator.getType().equals("IMG"))
                    UserSetter.updatePhoto(creator.getUsername(), creator.getPath());
                /*else
                    UserAccesor.uploadFile(creator.getUsername(), partFile.getName());*/

                response.setStatus(200);
            }
        } catch (InvalidInputType | IllegalStateException | NullPointerException |
                    TooManyArgumentsException e) {
            response.sendError(400, e.getMessage());
        } catch (IOException | SQLException e) {
            response.sendError(500, e.getMessage());
        }
    }

}
