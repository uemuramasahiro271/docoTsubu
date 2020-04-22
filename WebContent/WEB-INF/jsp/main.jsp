<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="model.Mutter" %>
<%@ page import="java.util.List" %>

<%
	@SuppressWarnings("unchecked")
	List<Mutter> mutterList = (List<Mutter>)application.getAttribute("mutterList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
	<h1>どこつぶメイン</h1>
	<p>
		${loginUser.name} さん、ログイン中<br>
		<a href="/docoTsubu/Logout">ログアウト</a>
	</p>
	<p><a href="/docoTsubu/Main">更新</a></p>
	<form action="/docoTsubu/Main" method="post">
		<input type="text" name="text">
		<input type="submit" value="つぶやく">
	</form>
	<% for(Mutter mutter : mutterList) { %>
		<p>
			<%= mutter.getUserName() %> : <%= mutter.getText() %>
		</p>
	<% } %>

	<br>

	<jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>