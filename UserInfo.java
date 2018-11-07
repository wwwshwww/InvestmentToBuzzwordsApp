/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzbuzz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

/**
 *
 * @author wwwsh
 */
public class UserInfo {

    private String name;
    private String id;
    private double availablePoint;
    private double buzzly;
    private List<InvestInfo> invesList;

    public UserInfo(){
        name = "";
        id = "";
        availablePoint = 0.0;
        buzzly = 0.0;
        invesList = new ArrayList<>();
    }
    //新規ユーザー用
    public UserInfo(String name, String id) {
        this.name = name;
        this.id = id;
        availablePoint = 10000.0;
        buzzly = 0.0;
        invesList = new ArrayList<>();
    }

    public UserInfo(String name, String id, int availablePoint, double buzzly) {
        this.name = name;
        this.id = id;
        this.availablePoint = availablePoint;
        this.buzzly = buzzly;
        invesList = new ArrayList<>();
    }

    public void resetUserInfo(String id) throws ServletException {
        invesList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/buzzword", "db", "db");

            ps = con.prepareStatement("select * from USER_INFO where USER_ID=?");
            ps.setString(1, id);
            ResultSet userResult = ps.executeQuery();
            userResult.next();
            
            this.name = userResult.getString("USER_NAME");
            this.id = userResult.getString("USER_ID");
            this.availablePoint = userResult.getDouble("AVAILABLE_POINT");
            this.buzzly = userResult.getDouble("USER_BUZZLY");
            
            ps = con.prepareStatement("select * from INVEST_INFO where USER_ID=?");
            ps.setString(1, id);
            ResultSet investResult = ps.executeQuery();
            while(investResult.next()){
                invesList.add(new InvestInfo(investResult.getString("USER_ID"), 
                        investResult.getString("INVEST_WORD"), 
                        investResult.getDouble("INVEST_VALUE"), 
                        investResult.getDouble("PRICE_THEN")));
            }
        }catch(Exception e){
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
    
    public List<InvestInfo> getList(){
        return invesList;
    }
    
    public double getUsingPoint(){
        double total = 0;
        for (InvestInfo investInfo : invesList) {
            total += investInfo.getInvestValue();
        }
        return total;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAvailablePoint(double availablePoint) {
        this.availablePoint = availablePoint;
    }

    public void setBuzzly(double buzzly) {
        this.buzzly = buzzly;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getAvailablePoint() {
        return availablePoint;
    }

    public double getBuzzly() {
        return buzzly;
    }
}
