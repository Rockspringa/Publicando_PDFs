package edu.publishPDF.xhr.users;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.publishPDF.database.accesors.AccesorTools;
import edu.publishPDF.database.accesors.user.UserGetter;
import edu.publishPDF.model.users.UserType;

@WebServlet(name = "AuthenticateUsuario", urlPatterns = { "/usuario/authenticate" })
public class AuthenticateUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Cookie sessionCk = searchCookie(request.getCookies(), "logeado");
            String[] data = AccesorTools.cookieJsonToData(sessionCk.getValue());

            String totalData = UserGetter.getUserData(data[1], UserType.valueOf(data[2]));
            if (totalData != null) {
                Cookie outCk = new Cookie("autorizado", "ok");
                outCk.setMaxAge(60);
                sessionCk.setMaxAge(10 * 60);

                response.addCookie(outCk);
                response.getWriter().print("autorizado");
            } else {
                response.sendError(400, "Hubo un error al autenticar al usuario.");
            }
        } catch (Exception e) {
            response.sendError(400, "Hubo un error al autenticar al usuario.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private Cookie searchCookie(Cookie[] cookies, String search) {
        if (cookies != null) {
            for (Cookie ck : cookies) {
                if (search.equals(ck.getName()))
                    return ck;
            }
        }
        return null;
    }

}
