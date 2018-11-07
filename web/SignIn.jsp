<%-- 
    Document   : SignIn
    Created on : 2017/12/23, 18:26:00
    Author     : wwwsh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ログイン</title>
    </head>
    <body>
        <h1>ログイン</h1>
        <%
            boolean diffPass;
            boolean notFoundId;
            String id;
            
            try {
                //ログインに失敗して再びフォワードされた時のみこの値がセットされる
                diffPass = (boolean) request.getAttribute("diffPass");
                notFoundId = (boolean) request.getAttribute("notFoundId");
                id = (String) request.getAttribute("id"); //ID再入力の手間省略用
            } catch (Exception e) {
                diffPass = false;
                notFoundId = false;
                id = "";
            } finally {
                session.invalidate(); //このページでセッションを必ず破棄する
            }
        %>
        <form action="SignIn" method="post">
            <p>
                <% if (diffPass) { %>
                ※パスワードが違います
                <% } %>
                <% if (notFoundId) { %>
                ※存在しないIDです
                <% }%>
            </p>
            <table>
                <tr>
                    <td>ユーザーID</td>
                    <td><input type="text" name="user_id" value="<%=id%>"></td>
                </tr>
                <tr>
                    <td>パスワード</td>
                    <td><input type="password" name="password"></td>
                </tr>
                <tr align="right">
                    <td colspan="2"><input type="submit" name="btn" value="ログイン"></td>
                </tr>
            </table>
        </form>
        <p>
            <a href="SignUp.jsp">新規登録はこちら</a>
        </p>
        <p>
            <a href="Top.html">トップへ戻る</a>
        </p>
    </body>
</html>
