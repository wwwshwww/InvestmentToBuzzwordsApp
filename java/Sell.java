/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzbuzz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "Sell", urlPatterns = {"/buzzbuzz/Sell"})
public class Sell extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        Connection con = null;
        PreparedStatement ps = null;
        try {
            RequestDispatcher dis = request.getRequestDispatcher("TradePage");
            HttpSession session = request.getSession(true);
            UserInfo user = (UserInfo) session.getAttribute("currentUser");
            user.resetUserInfo(user.getId());
            String selectedWord = request.getParameter("sellRadio");

            if (selectedWord != null) {
                List<InvestInfo> investInfo = user.getList();
                double priceNow = 0;
                double priceThen = 0;
                double investValue = 0;
                double returnValue = 0;
                double addBuzzly = 0;
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/buzzword", "db", "db");

                ps = con.prepareStatement("select PRICE from BUZZWORDS where WORD=?");
                ps.setString(1, selectedWord);
                ResultSet rs = ps.executeQuery();
                rs.next();

                priceNow = rs.getDouble("PRICE");

                for (InvestInfo info : investInfo) {
                    if (info.getWord().equals(selectedWord)) {
                        priceThen = info.getPriceThen();
                        investValue = info.getInvestValue();
                        break;
                    }
                }
                returnValue = investValue * (priceNow / priceThen);
                addBuzzly = priceNow / priceThen - 1.0;

                ps = con.prepareStatement("update BUZZWORDS set PRICE=PRICE-? where WORD=?");
                ps.setDouble(1, investValue);
                ps.setString(2, selectedWord);
                ps.executeUpdate();

                ps = con.prepareStatement("update USER_INFO set AVAILABLE_POINT=AVAILABLE_POINT+? "
                        + "where USER_ID=?");
                ps.setDouble(1, returnValue);
                ps.setString(2, user.getId());
                ps.executeUpdate();
                
                ps = con.prepareStatement("update USER_INFO set USER_BUZZLY=USER_BUZZLY+? where USER_ID=?");
                ps.setDouble(1, addBuzzly);
                ps.setString(2, user.getId());
                ps.executeUpdate();
                
                ps = con.prepareStatement("delete from INVEST_INFO where INVEST_WORD=? AND USER_ID=?");
                ps.setString(1, selectedWord);
                ps.setString(2, user.getId());
                ps.executeUpdate();
                
                user.resetUserInfo(user.getId());
            }
            dis.forward(request, response);
        }catch (Exception e) {
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
