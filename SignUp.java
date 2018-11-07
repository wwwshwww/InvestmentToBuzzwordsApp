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
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "SignUp", urlPatterns = {"/buzzbuzz/SignUp"})
public class SignUp extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>登録の確認</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>登録の確認</h1>");
            request.setCharacterEncoding("UTF-8");

            String name = request.getParameter("user_name");
            String id = request.getParameter("user_id");
            String password = request.getParameter("password");

            HttpSession signupInfo = request.getSession(true);
            signupInfo.setAttribute("name", name);
            signupInfo.setAttribute("id", id);
            signupInfo.setAttribute("password", password);
            signupInfo.setAttribute("blank", false);
            signupInfo.setAttribute("sameId", false);
 
            RequestDispatcher dis = request.getRequestDispatcher("SignUp.jsp");

            if (name.equals("") || id.equals("") || password.equals("")) {
                signupInfo.setAttribute("blank", true);
                dis.forward(request, response);
            } else {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/buzzword", "db", "db");
                ps = con.prepareStatement("select USER_ID from USER_INFO where USER_ID=?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    signupInfo.setAttribute("sameId", true);
                    dis.forward(request, response);
                } else {
                    out.println("<h4>以下の情報で登録しますか？</h4>");
                    out.println("<p>ニックネーム ： " + name + "<br>");
                    out.println("ユーザーID ： " + id + "<br>");
                    out.println("パスワード ： " + password + "</p>");

                    UserInfo user = new UserInfo(name, id);
                    signupInfo.setAttribute("user", user);

                    out.println("<form action='SignUp.jsp' method='post'>");
                    out.println("<p><input type='submit' value='修正する'></p>");
                    out.println("</form>");
                    out.println("<form action='SignUpComplete' method='post'>");
                    out.println("<p><input type='submit' value='登録する'></p>");
                    out.println("</form>");
                }
            }
            out.println("</body></html>");

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
