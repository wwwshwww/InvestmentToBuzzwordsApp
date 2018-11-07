<%-- 
    Document   : PostRemoveWord
    Created on : 2017/12/29, 0:53:26
    Author     : wwwsh
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="buzzbuzz.PostedWordInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buzzwordの追加・削除</title>
    </head>
    <body>
        <%
            List<PostedWordInfo> words = (ArrayList<PostedWordInfo>) request.getAttribute("words");
            request.setAttribute("word", words);
        %>
        <h1>Buzzwordの追加・削除</h1>
        <form action="PostWord" method="post">
            <input type="text" name="newWord"/>
            <input type="submit" value="追加"/>
        </form>
        <hr/>
        <form action="RemoveWord" method="post">
            <table border="2">
                <tr>
                    <th>&nbsp;ワード&nbsp;</th>
                    <th>&nbsp;現在価値(P)&nbsp;</th>
                    <th>&nbsp;追加日時&nbsp;</th>
                    <th>&nbsp;削除&nbsp;</th>
                </tr>
                <% for (PostedWordInfo word : words) {%>
                <tr>
                    <td><%= word.getWord()%></td>
                    <td align="right"><%= word.getPrice()%> P</td>
                    <td><%= word.getPostedTime()%></td>
                    <td align="center"><input type="checkbox" name="<%= word.getWord()%>" value="1"/></td>
                </tr>
                <% }%>
            </table>
            <br/>
            <% if (!words.isEmpty()) {%>
            <input type="submit" value="選択したワードを削除"/>
            <% }%>
        </form>
        <hr>
        <a href="Manager.html">管理者ページへ戻る</a>
    </body>
</html>
