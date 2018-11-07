<%-- 
    Document   : UserInfo
    Created on : 2018/01/09, 11:19:42
    Author     : wwwsh
--%>

<%@page import="buzzbuzz.InvestInfo"%>
<%@page import="buzzbuzz.UserInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ユーザー情報の確認</title>
    </head>
    <body>
        <%
            List<UserInfo> userList = (ArrayList<UserInfo>) request.getAttribute("users");
        %>
        <h1>ユーザー情報の確認</h1>
        <h2>ユーザー検索</h2>
        <form action="SearchUser" method="post">
            <select name="selectNumData">
                <option value="AVAILABLE_POINT" selected>ポイント残高</option>
                <option value="INVEST_VALUE">使用中ポイント</option>
                <option value="USER_BUZZLY">通算評価値</option>
            </select>&nbsp;
            <input type="text" name="searchValue" value="0">
            <input type="radio" name="radioUpLow" value="upper" checked="checked">以上
            <input type="radio" name="radioUpLow" value="lower">以下&nbsp;
            <input type="submit" name="pbtn" value="検索">
        </form>
        <% for (UserInfo user : userList) {%>
        <hr/>
        <h3><%=user.getName()%></h3>
        <table border="1">
            <tr>
                <th>&nbsp;ユーザーID&nbsp;</th>
                <th>&nbsp;ポイント残高&nbsp;</th>
                <th>&nbsp;使用中ポイント&nbsp;</th>
                <th>&nbsp;通算評価値&nbsp;</th>
            </tr>
            <tr>
                <td><%=user.getId()%></td>
                <td align="right"><%=user.getAvailablePoint()%></td>
                <td align="right"><%=user.getUsingPoint()%></td>
                <td align="right"><%=user.getBuzzly()%></td>
            </tr>
        </table>
        <p>保有ワード</p>

        <%if (user.getList().isEmpty()) {%>
        なし
        <%}%>
        <ul>
            <%for (InvestInfo info : user.getList()) {%>
            <li><%=info.getWord()%>&nbsp;:&nbsp;<%=info.getInvestValue()%>&nbsp;P</li>
            <%}%>
        </ul>
        <%}%>
        <hr/>
        <p>
            <a href="Manager.html">管理者ページへ</a>
        </p>
    </body>
</html>
