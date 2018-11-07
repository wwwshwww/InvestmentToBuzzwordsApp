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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wwwsh
 */
@WebServlet(name = "SearchUser", urlPatterns = {"/buzzbuzz/SearchUser"})
public class SearchUser extends HttpServlet {

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
        List<UserInfo> users = new ArrayList<>();
        List<String> resultIDs = new ArrayList<>();
        RequestDispatcher dis = request.getRequestDispatcher("AllUserInfo.jsp");
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/buzzword", "db", "db");
            boolean successFormat = false;
            double searchValue = 0.0;
            String selectUpLow = request.getParameter("radioUpLow"); // upper, lower
            String uplowSql = ""; // >=, <=
            String selectNumData = request.getParameter("selectNumData");
            if (selectUpLow.equals("upper")) {
                uplowSql = ">=";
            } else if (selectUpLow.equals("lower")) {
                uplowSql = "<=";
            }
            try {
                searchValue = Double.parseDouble(request.getParameter("searchValue"));
                successFormat = true;
            } catch (NumberFormatException e) {
                successFormat = false;
            }
            if (successFormat) {
                ResultSet rs;
                if (selectNumData.equals("AVAILABLE_POINT")) {
                    ps = con.prepareStatement("select USER_ID from USER_INFO "
                            + "where AVAILABLE_POINT " + uplowSql + " ?");
                    ps.setDouble(1, searchValue);
                    rs = ps.executeQuery();
                } else if (selectNumData.equals("INVEST_VALUE")) {
                    ps = con.prepareStatement("select USER_ID from INVEST_INFO group by USER_ID "
                            + "having sum(INVEST_VALUE) " + uplowSql + " ?");
                    ps.setDouble(1, searchValue);
                    rs = ps.executeQuery();
                } else if (selectNumData.equals("USER_BUZZLY")) {
                    ps = con.prepareStatement("select USER_ID from USER_INFO "
                            + "where USER_BUZZLY " + uplowSql + " ?");
                    ps.setDouble(1, searchValue);
                    rs = ps.executeQuery();
                } else {
                    // 実際にこの部分に処理が来ることはない
                    ps = con.prepareStatement("select USER_ID from USER_INFO");
                    rs = ps.executeQuery();
                }
                while (rs.next()) {
                    UserInfo user = new UserInfo();
                    user.resetUserInfo(rs.getString("USER_ID"));
                    users.add(user);
                }
                rs.close();
            }
            request.setAttribute("users", users);
            dis.forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
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
