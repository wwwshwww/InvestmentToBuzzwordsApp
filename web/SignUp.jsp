<%-- 
    Document   : SignUp
    Created on : 2017/12/23, 18:31:54
    Author     : wwwsh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>新規登録</title>
    </head>
    <body>
        <h1>新規登録</h1>
        <%
            boolean blank;
            boolean sameId;
            String name;
            String id;
            String password;
            try {
                blank = (boolean) session.getAttribute("blank");
                sameId = (boolean) session.getAttribute("sameId");
                name = (String) session.getAttribute("name");
                id = (String) session.getAttribute("id");
                password = (String)session.getAttribute("password");
            }catch(Exception e){
                blank = false;
                sameId = false;
                name = "";
                id = "";
                password = "";
            }finally{
                session.invalidate();
            }
        %>
        <form action="SignUp" method="post">
            <p>
                <% if (blank) { %>
                ※全ての入力欄を埋めてください<br>
                <% } %>
                <% if (sameId) { %>
                ※既に同じIDが登録されています<br>
                <% }%>
            </p>
            <table>
                <tr>
                    <td>ニックネーム</td>
                    <td><input type="text" name="user_name" value="<%=name%>"></td>
                </tr>
                <tr>
                    <td>ユーザーID</td>
                    <td><input type="text" name="user_id" value="<%=id%>"></td>
                </tr>
                <tr>
                    <td>パスワード</td>
                    <td><input type="password" name="password" value="<%=password%>"></td>
                </tr>
                <tr align="right">
                    <td colspan="2"><input type="submit" name="btn" value="新規登録"></td>
                </tr>
            </table>
        </form>
        <p>
            <a href="SignIn.jsp">ログインページへ戻る</a>
        </p>
    </body>
</html>
