<%-- 
    Document   : TradePage
    Created on : 2018/01/01, 17:27:12
    Author     : wwwsh
--%>

<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, java.util.List"%>
<%@page import="buzzbuzz.UserInfo, buzzbuzz.InvestInfo, buzzbuzz.PostedWordInfo"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>取引所</title>
    </head>
    <body>
        <h1>取引所</h1>
        <%
            UserInfo user = (UserInfo) session.getAttribute("currentUser");
            user.resetUserInfo(user.getId()); //ユーザー情報を最新の状態に更新
            Map<String, PostedWordInfo> words = (LinkedHashMap<String, PostedWordInfo>) request.getAttribute("words");
        %>
        <p>
            <%=user.getName()%>&nbsp;さん<br/>
            ポイント残高：<%=user.getAvailablePoint()%> P<br/>
            使用中：<%=user.getUsingPoint()%>&nbsp;P
        </p>
        <p><a href="MyPage.jsp">マイページへ</a></p>
        <hr/>
        <h2>売却</h2>
        <%if (user.getList().isEmpty()) {%>
        現在保有しているワードはありません
        <%} else {%>
        <form action="Sell" method="post">
            <table border="2">
                <tr>
                    <th>&nbsp;保有ワード&nbsp;</th>
                    <th>&nbsp;現在価値&nbsp;</th>
                    <th>&nbsp;購入時価値&nbsp;</th>
                    <th>&nbsp;購入量(P)&nbsp;</th>
                    <th>&nbsp;売却選択&nbsp;</th>
                </tr>
                <%for (InvestInfo info : user.getList()) {%>
                <tr>
                    <td><%=info.getWord()%></td>
                    <td align="right"><%=words.get(info.getWord()).getPrice()%></td>
                    <td align="right"><%=info.getPriceThen()%></td>
                    <td align="right"><%=info.getInvestValue()%></td>
                    <td align="center"><input type="radio" name="sellRadio" value="<%=info.getWord()%>"/></td>
                </tr>
                <%}%>
            </table>
            <p><input type="submit" name="sellBtn" value="売却"/></p>
        </form>
        <%}%>
        <hr/>
        <h2>購入</h2>
        <p>
            ※同じワードの追加購入はできません。また、同時に保有できるワードは最大<strong>3つ</strong>までとなります。
        </p>
        <form action="Buy" method="post">
            <table border="2">
                <tr>
                    <th>&nbsp;ワード&nbsp;</th>
                    <th>&nbsp;現在価値&nbsp;</th>
                    <th>&nbsp;購入選択&nbsp;</th>
                </tr>
                <% for (String key : words.keySet()) {%>
                <tr>
                    <td><%=words.get(key).getWord()%></td>
                    <td align="right"><%=words.get(key).getPrice()%></td>
                    <td align="center"><input type="radio" name="buyRadio" value="<%=words.get(key).getWord()%>"/></td>
                </tr>
                <%}%>
            </table>
            <p>
                購入量：<input type="text" name="investValue" align="right"/>&nbsp;P
                <input type="submit" name="buyBtn" value="購入"/>
            </p>
        </form>
    </body>
</html>
