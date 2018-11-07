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
@WebServlet(name = "SignIn", urlPatterns = {"/buzzbuzz/SignIn"})
public class SignIn extends HttpServlet {

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

            request.setCharacterEncoding("UTF-8");
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/buzzword", "db", "db");

            String id = request.getParameter("user_id");
            String password = request.getParameter("password");

            boolean diffPass = false; //パスワードが間違っている場合はtrueにする
            boolean notFoundId = false; //IDがデータベース上に存在しない場合はtrueにする

            //入力IDがメンバーデータにあるかチェック
            ps = con.prepareStatement("select * from MEMBER where USER_ID=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            RequestDispatcher dispatcher;

            if (rs.next()) {
                if (password.equals(rs.getString("PASSWORD"))) {
                    //ログイン成功
                    UserInfo currentUser = new UserInfo();
                    currentUser.resetUserInfo(id);
                    HttpSession loginSes = request.getSession(true);
                    loginSes.setAttribute("currentUser", currentUser);
                    dispatcher = request.getRequestDispatcher("MyPage.jsp");
                } else {
                    diffPass = true; //パスワード間違いフラグ
                    dispatcher = request.getRequestDispatcher("SignIn.jsp");
                }
            } else {
                notFoundId = true; //IDが存在しないフラグ
                dispatcher = request.getRequestDispatcher("SignIn.jsp");
            }
            request.setAttribute("id", id); //IDは入力されている状態のままにするため
            request.setAttribute("diffPass", diffPass);
            request.setAttribute("notFoundId", notFoundId);
            dispatcher.forward(request, response);

        } catch (Exception e) {
            //サーブレット内での例外をアプリケーションのエラーとして表示
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
