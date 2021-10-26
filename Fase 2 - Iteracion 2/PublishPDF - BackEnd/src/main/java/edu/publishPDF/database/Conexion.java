package edu.publishPDF.database;

import javax.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Conexion extends HttpServlet {

    private static int stmts = 0;
    private static Connection conn = null;
    private static final String URL = "jdbc:mysql://localhost:3306/PUBLISH_PDF";
    private static final String USER = "admin_publish-pdf";
    private static final String PASS = "nimda";

    public static boolean closeSession() throws SQLException {
        boolean success = false;

        if (conn != null && stmts == 0) {
            conn.close();
            success = true;
        } else
            stmts--;

        return success;
    }

    public static void setAutoCommit(boolean autoCommit) throws SQLException {
        if (conn != null)
            conn.setAutoCommit(autoCommit);
    }

    /**
     * Termina la transaccion actual.
     * 
     * @param commit es para saber si terminar la transaccion haciendo un commit o
     *               un rollback.
     * @throws SQLException
     */
    public static void finishTransaction(boolean commit) throws SQLException {
        if (conn != null)
            if (commit)
                conn.commit();
            else
                conn.rollback();
    }

    public static boolean createSession() throws SQLException {
        boolean success = true;

        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASS);
            } catch (ClassNotFoundException e) {
                success = false;
            }
        }

        return success;
    }

    public static PreparedStatement getPrepareStatement(String query) throws SQLException {
        stmts++;
        return Conexion.conn.prepareStatement(query);

    }
}