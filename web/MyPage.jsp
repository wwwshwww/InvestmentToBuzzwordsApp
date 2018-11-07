<%-- 
    Document   : MyPage
    Created on : 2017/12/24, 14:18:27
    Author     : wwwsh
--%>

<%@page import="buzzbuzz.InvestInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="buzzbuzz.UserInfo"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>マイページ</title>
    </head>
    <body>
        <h1>マイページ</h1>
        <%
            UserInfo user = (UserInfo) session.getAttribute("currentUser");
            user.resetUserInfo(user.getId());
        %>
        <p>
            ようこそ&nbsp;<b><%= user.getName()%></b>&nbsp;さん
        </p>
        <hr/>
        <h3>資産情報</h3>
        <table border="1">
            <tr>
                <th>&nbsp;ポイント残高&nbsp;</th>
                <th>&nbsp;使用中ポイント&nbsp;</th>
                <th>&nbsp;通算評価値&nbsp;</th>
            </tr>
            <tr>
                <td align="right"><%= user.getAvailablePoint()%></td>
                <td align="right"><%= user.getUsingPoint()%></td>
                <td align="right"><%= user.getBuzzly()%></td>
            </tr>
        </table>
        <h3>保有ワード</h3>
        <% if (user.getList().isEmpty()) {%>
            <p>現在保有しているワードはありません</p>
        <%} else {%>
            <ul>
            <%for (InvestInfo inv : user.getList()) {%>
                <li><%=inv.getWord()%></li>
            <%}%>
            </ul>
        <%}%>
        <p>
            <a href="TradePage">取引画面へ</a>
        </p>
        <hr/>
        <p>
            <a href="SignIn.jsp">ログアウト</a>
        </p>
    </body>
</html>
