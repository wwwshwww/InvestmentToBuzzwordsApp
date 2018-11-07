/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzbuzz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wwwsh
 */
@WebServlet(name = "SignUpComplete", urlPatterns = {"/buzzbuzz/SignUpComplete"})
public class SignUpComplete extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Connection con = null;
        PreparedStatement ps = null;

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>登録完了</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>登録完了</h1>");

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/buzzword", "db", "db");

            request.setCharacterEncoding("UTF-8");
            HttpSession signupInfo = request.getSession(true);
            UserInfo user = (UserInfo)signupInfo.getAttribute("user");
            String password = (String)signupInfo.getAttribute("password");

            String insertMember = "insert into MEMBER(USER_ID, PASSWORD) values(?, ?)";
            String insertUserInfo = "insert into USER_INFO"
                    + "(USER_ID, USER_NAME, AVAILABLE_POINT, USER_BUZZLY) "
                    + "values(?, ?, ?, ?)";
            
            ps = con.prepareStatement(insertMember);
            ps.setString(1, user.getId());
            ps.setString(2, password);
            ps.executeUpdate();
            
            ps = con.prepareStatement(insertUserInfo);
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setDouble(3, user.getAvailablePoint());
            ps.setDouble(4, user.getBuzzly());
            ps.executeUpdate();
            
            signupInfo.invalidate();
            
            out.println("<p>登録が完了しました</p>");
            out.println("<p><a href='SignIn.jsp'>ログインページへ</a></p>");

            out.println("</body>");
            out.println("</html>");
            
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            //例外が発生する・しないにかかわらず確実にデータベースから切断
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
